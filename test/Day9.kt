import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class Day9 {

    @ParameterizedTest
    @CsvSource(
        "5,5: 5,5: 1",          // a single tile (impossible given the puzzle input, we won't overlap tiles, but let's see)
        "2,5: 9,7: 24",                  // an example from AoC
        "7,1: 11,7: 35",                 // an example from AoC
        "7,3: 2,3: 6",                   // an example from AoC (thin rectangle)
        "2,5: 11,1: 50",                 // an example from AoC (largest in the example)
        delimiter = ':',
    )
    fun testGetArea(p1: String, p2: String, expected: Int) {
        val point1 = Point(
            p1.split(",")[0].toInt(),
            p1.split(",")[1].toInt())

        val point2 = Point(
            p2.split(",")[0].toInt(),
            p2.split(",")[1].toInt())

        val result = getArea(point1, point2)
        assertEquals(expected, result)
    }
}
