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

fun day5part2(rawInput: String): Int {
    val (freshRanges, _) = parseInput(rawInput)

    return freshRanges
        .flatMap { it }     // this takes a long time, maybe it's allocating too many numbers
        .toSet()
        .count()
}

fun isFresh(ingredient: Long, freshRanges: List<LongRange>): Boolean {
    return freshRanges.any { ingredient in it }
}