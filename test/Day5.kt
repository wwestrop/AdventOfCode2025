import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class Day5 {

    @ParameterizedTest
    @CsvSource(
        "0-10: 10-20: [0..20]",      // adjoining
        "10-20: 30-40: [10..20, 30..40]",     // disjoint
        "10-20: 0-10: [0..20]",               // adjoining but inverted
        "10-20: 10-20: [10..20]",             // equal
        "4-9: 1-10: [1..10]",                 // subset
        "1-10: 4-9: [1..10]",                 // superset
        "10-20: 15-25: [10..25]",             // spans over the end
        "15-25: 10-20: [10..25]",             // spans over the start
        delimiter = ':',
    )
    fun testMergeRanges(range1: String, range2: String, expected: String) {
        val ranges = listOf(
            LongRange(range1.split("-")[0].toLong() , range1.split("-")[1].toLong()),
            LongRange(range2.split("-")[0].toLong() , range2.split("-")[1].toLong()),
            )

        val result = repeatedlyMergeRanges(ranges)

        assertEquals(expected, result.toString())
    }

    @Test
    fun testMergeRanges_mixOfAllTypes() {
        val ranges = listOf(
            LongRange(0L, 20L),
            LongRange(5L, 8L),       // contained within the first range
            LongRange(20L, 25L),     // abuts the first range
            LongRange(15L, 30L),     // overlaps end of first range

            LongRange(70L, 75L),     // completely disjoint from the first

            LongRange(100L, 110L),   // completely disjoint from anything else again
            LongRange(95L, 105L),    // but then this overlaps the start
            LongRange(90L, 95L),     // and this adjoins the front of what would be the subsequent merged range
        )

        val shuffled = ranges.shuffled()
        println("Shuffled input: $shuffled")
        val result = repeatedlyMergeRanges(shuffled).sortedBy { it.start }

        assertEquals("[0..30, 70..75, 90..110]", result.toString())
    }
}
