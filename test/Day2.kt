import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class Day2 {

    @ParameterizedTest
    @CsvSource(
        "11, true",
        "22, true",
        "1010, true",
        "1188511885, true",
        "222222, true",
        "446446, true",
        "4464460, false",
        "38593859, true"
    )
    fun testIsInvalid(input: Long, expected: Boolean) {
        val result = isInvalid(input)

        assertEquals(expected, result)
    }
}
