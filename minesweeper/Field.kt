package minesweeper

import kotlin.random.Random

class Field {

    private val value: Array<Array<Cell>>
    private val mines: ArrayList<Cell> = arrayListOf()
    private var markedCellsCount: Int = 0
    private var openedCellsCount: Int = 0
    private var gameOver: Boolean = false

    init {
        val size = 9
        value = Array(size) { row ->
            Array(size) { column ->
                Cell(row = row, column = column, value = '/')
            }
        }

        for (row in 0 until size) {
            for (column in 0 until size) {
                val cell = value[row][column]

                cell.up = if (row > 0) value[row - 1][column] else null
                cell.down = if (row < size - 1) value[row + 1][column] else null
                cell.left = if (column > 0) value[row][column - 1] else null
                cell.right = if (column < size - 1) value[row][column + 1] else null
                cell.upLeft = if (row > 0 && column > 0) value[row - 1][column - 1] else null
                cell.upRight = if (row > 0 && column < size - 1) value[row - 1][column + 1] else null
                cell.downLeft = if (row < size - 1 && column > 0) value[row + 1][column - 1] else null
                cell.downRight = if (row < size - 1 && column < size - 1) value[row + 1][column + 1] else null
            }
        }
    }

    fun addMines() {
        print("How many mines do you want on the field? ")

        val minesCount = readln().toInt()
        val random = Random
        val indices = ArrayList<Int>()

        for (i in 0..80) {
            indices.add(i)
        }

        repeat(minesCount) {
            val mineIndex = indices.removeAt(random.nextInt(indices.size))
            val cell = value[mineIndex / 9][mineIndex % 9]

            cell.value = 'X'
            mines.add(cell)

            cell.up?.calculateNewValue()
            cell.down?.calculateNewValue()
            cell.left?.calculateNewValue()
            cell.right?.calculateNewValue()
            cell.upLeft?.calculateNewValue()
            cell.upRight?.calculateNewValue()
            cell.downLeft?.calculateNewValue()
            cell.downRight?.calculateNewValue()
        }
    }

    fun print() {
        println()
        println(" │123456789│")
        println("—│—————————│")

        for (i in value.indices) {
            print("${i + 1}|")
            printLine(value[i])
            println("│")
        }

        println("—│—————————│")
    }

    fun getCell(x: Int, y: Int): Cell {
        return value[y - 1][x - 1]
    }

    fun checkForWin() = markedCellsCount == mines.size && isAllMinesAreMarked() || openedCellsCount == 81 - mines.size

    fun checkForLose() = gameOver

    fun markMine(cell: Cell) {
        when (cell.state) {
            CellState.CLOSED -> {
                markedCellsCount++
                cell.state = CellState.MARKED
            }

            CellState.MARKED -> {
                markedCellsCount--
                cell.state = CellState.CLOSED
            }

            CellState.OPEN -> println("This cell is already open.")
        }

        print()
    }

    fun openFromCell(cell: Cell) {
        cell.state = CellState.OPEN
        openedCellsCount++

        when (cell.value) {
            'X' -> gameOver = true
            '/' -> {
                openIfNotOpen(cell.up)
                openIfNotOpen(cell.upRight)
                openIfNotOpen(cell.right)
                openIfNotOpen(cell.downRight)
                openIfNotOpen(cell.down)
                openIfNotOpen(cell.downLeft)
                openIfNotOpen(cell.left)
                openIfNotOpen(cell.upLeft)
            }
        }
    }

    private fun openIfNotOpen(cell: Cell?) {
        if (cell != null && cell.state != CellState.OPEN) {
            openFromCell(cell)
        }
    }

    private fun isAllMinesAreMarked(): Boolean {
        for (mine in mines) {
            if (mine.state != CellState.MARKED) {
                return false
            }
        }

        return true
    }

    private fun printLine(line: Array<Cell>) {
        for (cell in line) {
            when (cell.state) {
                CellState.OPEN -> print(cell.value)
                CellState.CLOSED -> print('.')
                CellState.MARKED -> print('*')
            }
        }
    }
}