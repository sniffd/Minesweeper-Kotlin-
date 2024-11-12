package minesweeper

fun main() {
    val field = Field()

    field.addMines()
    field.print()
    play(field)
}

fun play(field: Field) {
    while (true) {
        print("Set/delete mines marks (x and y coordinates): ")

        val input = readln().split(" ")
        val x = input[0].toInt()
        val y = input[1].toInt()
        val action = input[2]

        val cell = field.getCell(x, y)
        when (action) {
            "mine" -> {
                field.markMine(cell)

                if (field.checkForWin()) {
                    println("Congratulations! You found all the mines!")
                    break
                }
            }
            "free" -> {
                field.openFromCell(cell)
                field.print()

                if (field.checkForLose()) {
                    println("You stepped on a mine and failed!")
                    break
                }
            }
        }
    }
}

