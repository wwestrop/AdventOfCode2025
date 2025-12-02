import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class Day1 {

    @ParameterizedTest
    @CsvSource(
        "3, 1, 4",            // regular addition
        "3, -1, 2",           // regular subtraction"

        "3, 97, 0",           // addition to 0 boundary
        "3, -3, 0",           // subtraction to 0 boundary

        "3, 96, 99",          // addition to N boundary
        "3, -4, 99",          // subtraction to N boundary

        "3, 99, 2",           // addition past boundary
        "3, -5, 98",          // subtraction past boundary

        "3, 197, 0",          // addition to 0 boundary twice
        "3, -103, 0",         // subtraction to 0 boundary twice

        "3, 196, 99",         // addition to N boundary twice
        "3, -104, 99",        // subtraction to N boundary twice
    )
    fun testRotate(pointer: Int, num: Int, expected: Int) {
        val instruction = Instruction(Direction.RIGHT, num)
        val result = rotate(pointer, instruction)

        assertEquals(expected, result)
    }

}
