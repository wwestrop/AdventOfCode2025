import kotlin.collections.filter
import kotlin.collections.map
import kotlin.text.trim

private fun parseRange(input: String): LongRange {
    val parts = input .split("-")
    return LongRange(parts[0].toLong(), parts[1].toLong())
}

private fun parseInput(input: String): Pair<List<LongRange>, List<Long>> {
    val (part1, part2) = input.replace("\r\n\r\n", "\n\n").split("\n\n")

    val ranges = part1
        .lines()
        .filter { !it.trim().isEmpty() }
        .map { it.trim() }
        .map { parseRange(it) }

    var ingredients = part2
        .lines()
        .filter { !it.trim().isEmpty() }
        .map { it.trim() }
        .map { it.toLong() }

    return Pair(ranges, ingredients)
}


fun day5(rawInput: String): Int {
    val (freshRanges, ingredients) = parseInput(rawInput)

    return ingredients
        .filter { isFresh(it, freshRanges) }
        .count()
}

fun day5part2(rawInput: String): Long {
    val (freshRanges, _) = parseInput(rawInput)

    var mergedRanges = repeatedlyMergeRanges(freshRanges)

    return mergedRanges
        .map { it.endInclusive - it.start + 1 }
        .sum()
}

fun isFresh(ingredient: Long, freshRanges: List<LongRange>): Boolean {
    return freshRanges.any { ingredient in it }
}

internal fun repeatedlyMergeRanges(ranges: List<LongRange>): List<LongRange> {

    var result = ranges

    var i = 0
    while (i < 100) {
        val resultLen = result.size
        result = mergeRanges(result)
        if (result.size == resultLen) {
            return result
        }

        i++
    }

    throw Exception("Probable infinite loop")
}

internal fun mergeRanges(ranges: List<LongRange>): List<LongRange> {

    val resultRanges = ranges.toMutableList()

    var i = resultRanges.size - 1
    while (i >= 0) {
        val range = resultRanges[i]

        val adjoiningRangeIndex = resultRanges.indexOfFirst { resultRanges.indexOf(it) < i && (it.endInclusive == range.start || range.endInclusive == it.start) }
        if (adjoiningRangeIndex != -1) {
            val adjoiningRange = resultRanges[adjoiningRangeIndex]

            // merge with its adjoining range
            resultRanges[adjoiningRangeIndex] = Math.min(adjoiningRange.start, range.start)..Math.max(adjoiningRange.endInclusive, range.endInclusive)
            resultRanges.removeAt(i)
            i--
        }
        i--
    }

    // subsets or equal ranges
    i = resultRanges.size - 1
    while (i >= 0) {
        val range = resultRanges[i]

        val supersetRangeIndex = resultRanges.indexOfFirst { resultRanges.indexOf(it) < i && it.start <= range.start && it.endInclusive >= range.endInclusive }
        if (supersetRangeIndex != -1) {
            // Remove the smaller set
            resultRanges.removeAt(i)
            i--
        }
        i--
    }

    // this spans the end of another range
    i = resultRanges.size - 1
    while (i >= 0) {
        val range = resultRanges[i]

        val overlappingRangeIndex = resultRanges.indexOfFirst { resultRanges.indexOf(it) < i && it.start <= range.endInclusive && it.endInclusive >= range.start }
        if (overlappingRangeIndex != -1) {
            val overlappingRange = resultRanges[overlappingRangeIndex]

            // merge with its overlapping range
            resultRanges[overlappingRangeIndex] = Math.min(overlappingRange.start, range.start)..Math.max(overlappingRange.endInclusive, range.endInclusive)
            resultRanges.removeAt(i)
            i--
        }
        i--
    }

    return resultRanges
}