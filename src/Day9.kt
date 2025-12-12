import java.lang.Math
import kotlin.math.abs
import kotlin.math.max

private fun parseLine(input: String): Point {
    val parts = input
        .trim()
        .split(",")
        .map { it.toInt() }

    return Point(parts[0], parts[1])
}

fun day9(rawInput: String): Long {
    val input = parseLines(rawInput, ::parseLine)

    var biggestRectangle = 0L

    for (i in 0..<input.size) {
        for (j in i + 1..<input.size) {
            val point1 = input[i]
            val point2 = input[j]
            biggestRectangle = max(biggestRectangle, getArea(point1, point2))
        }
    }

    return biggestRectangle
}

internal fun getArea(from: Point, to: Point): Long {
    val width = abs(to.x - from.x) + 1
    val height = abs(to.y - from.y) + 1

    return width.toLong() * height.toLong()
}