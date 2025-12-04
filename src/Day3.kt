private fun parseLine(input: String): List<UShort> {
    return input.map { it.toString().toUShort() }
}


fun day3(rawInput: String): UInt {
    val input = parseLines(rawInput, ::parseLine)
    return input
        .map { getJoltage(it) }
        .sum()
}


internal fun getJoltage(list: List<UShort>): UInt {

    for (i in 9 downTo 0) {

        // if this digit is in the list, find the earliest
        val i2 = i.toUShort()        // WHY KOTLIN, WHY. I always regret using anything but "int" by default
        val j = list.indexOf(i2)
        if (j == -1 || j == list.count() - 1) {         // exclude last digit as we need to find the second to its right
            continue
        }

        val mostSignificantDigit = i2

        // find the largest digit to the right of this
        val leastSignificantDigit = list
            .slice(j + 1..<list.count())
            .sortedDescending()
            .first()

        return mostSignificantDigit * 10u + leastSignificantDigit
    }

    return 0u;
}