package minesweeper

class Cell(
    val row: Int,
    val column: Int,
    val next: Cell? = null,
    var up: Cell? = null,
    var down: Cell? = null,
    var left: Cell? = null,
    var right: Cell? = null,
    var upLeft: Cell? = null,
    var upRight: Cell? = null,
    var downLeft: Cell? = null,
    var downRight: Cell? = null,
    var value: Char,
    var state: CellState = CellState.CLOSED
) {

    override fun toString(): String {
        return value.toString()
    }

    fun calculateNewValue() {
        value = when (value) {
            '/' -> '1'
            '1' -> '2'
            '2' -> '3'
            '3' -> '4'
            '4' -> '5'
            '5' -> '6'
            '6' -> '7'
            '7' -> '8'
            else -> value
        }
    }

}