import kotlin.sequences.count
import kotlin.sequences.last
import kotlin.sequences.take

private fun parseLine(input: String): List<String> {
    val regex = Regex("\\s+")
    val rows = input.split(regex)
    return rows
}

fun day6(rawInput: String): Long {
    val input = parseLines(rawInput, ::parseLine)

    var accumulator = 0L
    val numCols = input[0].count()
    for (i in 0..<numCols) {
        val col = input.getColumn(i)
        val colSize = col.count()
        val op = col.last()

        val colNums = col.take(colSize - 1).map { it.toLong() }
        when (op) {
            "+" -> accumulator += colNums.sum()
            "*" -> accumulator += colNums.reduce { acc, v -> acc * v }      // Likely to overflow I think
        }
    }

    return accumulator
}
