import kotlin.collections.count
import kotlin.sequences.count
import kotlin.sequences.take

private fun parseLine(input: String): List<String> {
    val regex = Regex("\\s+")
    val rows = input.split(regex).filter { it.isNotBlank() }
    return rows
}

private fun parseLine2(input: String, columnMapping: List<Int>): List<String> {
    var i = 0;

    val x = sequence {
        for (c in columnMapping) {
            yield(input.substring(i, i + c))
            i += c + 1
        }
    }

    return x.toList()
}

fun day6(rawInput: String): Long {
    val input = parseLines(rawInput, ::parseLine)

    var accumulator = 0L
    val numCols = input[0].count()
    for (i in 0..<numCols) {
        val col = input.getColumn(i)
        val colSize = col.count()
        val op = col.last()

        val longCols = col.take(colSize - 1).map { it.toLong() }
        when (op) {
            "+" -> accumulator += longCols.sum()
            "*" -> accumulator += longCols.reduce { acc, v -> acc * v }      // Likely to overflow I think
        }
    }

    return accumulator
}

fun day6part2(rawInput: String): Long {
    val input = parseLines(rawInput, ::parseLine)

    // hacky, use this to re-parse the file with known-width columns
    val largestNumInEachCol = getFixedWidthColumnMapping(input)
    val input2 = parseLines(rawInput) { parseLine2(it, largestNumInEachCol) }

    var accumulator = 0L

    val numCols = input[0].count()
    for (i in 0..<numCols) {
        val col = input2.getColumn(i)
        val colNumbers = col.take(col.count() - 1).toList()
        val op = input.last()[i]

        val longCols = pivot(colNumbers).map { it.toLong() }
        when (op) {
            "+" -> accumulator += longCols.sum()
            "*" -> accumulator += longCols.reduce { acc, v -> acc * v }
        }
    }

    return accumulator
}

fun getFixedWidthColumnMapping(rows: List<List<String>>): List<Int> {
    val result = mutableListOf<Int>()

    val numCols = rows[0].count()
    for (i in 0..<numCols) {
        val col = rows.getColumn(i)
        val biggestNumLength = col.maxOf { it.length }

        result.add(biggestNumLength)
    }

    return result
}

fun pivot(col: List<String>): List<String> {

    val pivotedNums = mutableListOf<String>()

    val colSize = col[0].length


    for (i in 0..<colSize) {
        var stringAccumulator = ""

        for (row in col) {
            val v = row.substring(i, i + 1)
            if (v != " ") {
                stringAccumulator += v
            }
        }

        pivotedNums.add(stringAccumulator)
    }

    return pivotedNums
}