import kotlin.math.abs


enum class Direction {
    LEFT,
    RIGHT,
}


data class Instruction(val direction: Direction, val num: Int)


private fun parseLine(input: String): Instruction {
    val dir = if (input[0] == 'L') Direction.LEFT else Direction.RIGHT

    return Instruction(
        direction = dir,
        num = input.substring(1).toInt())
}


fun day1(rawInput: String): Int {
    val input = parseLines(rawInput, ::parseLine)

    var pointer = 50
    var output = 0

    for (instruction in input) {

        pointer = rotate(pointer, instruction)

        if (pointer == 0) {
            output++
        }
    }

    return output
}


internal fun rotate(pointer: Int, instruction: Instruction): Int {
    val MAX_POINTER = 100

    val multipler = if (instruction.direction == Direction.LEFT) -1 else 1
    var newPointer = pointer + (multipler * instruction.num)

    if (newPointer >= MAX_POINTER) {
        newPointer = newPointer % MAX_POINTER
    }
    else if (newPointer < 0) {
        newPointer = MAX_POINTER - (abs(newPointer) % MAX_POINTER)
    }

    return if (newPointer == MAX_POINTER) 0 else newPointer
}