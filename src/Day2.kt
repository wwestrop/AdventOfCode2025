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


fun day2Part2(rawInput: String): Long {         // TODO it's probably going to overflow isn't it
    val input = parseLine(rawInput)

    return input
        .map { sumInvalidIds2(it) }
        .sum()
}


internal fun sumInvalidIds(range: LongRange): Long {
    return range
        .filter { isInvalid(it) }
        .sum()
}


internal fun sumInvalidIds2(range: LongRange): Long {
    return range
        .filter { isInvalid2(it) }
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

internal fun isInvalid2(num: Long): Boolean {
    val numString = num.toString()

   for (n in 2..numString.length) {
        if (isDuplicatedNTimes(numString, n)) {
            return true
        }
    }

    return false
}

fun isDuplicatedNTimes(numString: String, num: Int): Boolean {
    if (numString.length % num != 0) {
        // sequence doesn't fit neatly `num` times into the whole number
        return false
    }

    val seq = numString.substring(0, numString.length / num)

    return seq.repeat(num) == numString
}