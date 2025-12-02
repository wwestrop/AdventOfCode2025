private fun parseLine(input: String): List<LongRange> {
    return input
        .split(",")
        .map {
            val parts = it.split("-").map { it.toLong() }
            LongRange(parts[0], parts[1])
        }
}


fun day2(rawInput: String): Long {         // TODO it's probably going to overflow isn't it
    val input = parseLine(rawInput)

    return input
        .map { sumInvalidIds(it) }
        .sum()
}

internal fun sumInvalidIds(range: LongRange): Long {
    return range
        .filter { isInvalid(it) }
        .sum()
}


internal fun isInvalid(num: Long): Boolean {
    val numString = num.toString()

    if (numString.length % 2 != 0) {
        // cannot have a sequence repeated twice (and not have anything extra leftover)
        return false
    }

    val left = numString.substring(0, numString.length / 2)
    val right = numString.substring(numString.length / 2)

    return left == right
}