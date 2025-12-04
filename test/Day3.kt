import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class Day3 {

    @ParameterizedTest
    @CsvSource(
        "000001000, 10",
        "000001070, 70",
        "999999999, 99",
        "000000009, 9",
        "004000009, 49",
        "094000009, 99",
        "000000000, 0",
        "006200800, 80",
        "006200100, 62",
        "0123456789, 89",
        "08067, 87",
    )
    fun testGetJoltage(input: String, expected: UInt) {
        val battery = input.map { it.toString().toUShort() }
        val result = getJoltage(battery)

        assertEquals(expected, result)
    }
}
