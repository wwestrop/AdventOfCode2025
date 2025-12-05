import kotlin.sequences.sequence

private fun parseLine(input: String): List<Boolean> {
    return input
        .map { it == '@' }
}


fun day4(rawInput: String): Long {
    val input = parseLines(rawInput, ::parseLine)

    var accumulator = 0L
    for (y in 0..<input.size) {
        for (x in 0..<input[y].size) {
            val point = Point(x, y)
            if (input[point] && findAdjacentCells(input, point, { b -> b }).take(4).count() != 4) {
                accumulator++
            }
        }
    }
    return accumulator
}

fun day4Part2(rawInput: String): Long {
    val grid = parseLines(rawInput, ::parseLine).map { it.toMutableList() }.toMutableList()
    val rolls = grid
        .enumeratePoints()
        .filter { grid[it] }
        .toMutableList()

    var accumulator = 0L

    var i = rolls.size - 1
    while (true) {
        var removedThisCycle = 0
        while (i >= 0) {
            val roll = rolls[i]
            if (findAdjacentCells(grid, roll, { b -> b }).take(4).count() != 4) {
                accumulator++

                rolls.removeAt(i)
                grid[roll] = false
                removedThisCycle++
                i--
            }
            i--
        }

        if (removedThisCycle == 0) {
            break
        }

        i = rolls.size - 1
    }

    return accumulator
}

fun <T> findAdjacentCells(input: List<List<T>>, point: Point, predicate: (T) -> Boolean): Sequence<Point> {
    return sequence {
        // N
        var probed = point + Point(0, -1)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }

        // NE
        probed = point + Point(1, -1)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }

        // E
        probed = point + Point(1, 0)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }

        // SE
        probed = point + Point(1, 1)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }

        // S
        probed = point + Point(0, 1)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }

        // SW
        probed = point + Point(-1, 1)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }

        // W
        probed = point + Point(-1, 0)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }

        // NW
        probed = point + Point(-1, -1)
        if (!input.isOutOfBounds(probed)) {
            if (predicate(input[probed]))
                yield(probed)
        }
    }
}




// TODO wrap all this up into a grid class, because I have array-related shenanigans every year
// TODO although the loose overloaded operators are quite nice
// TODO I wonder if C# extension members would allow this now?
fun <T> List<List<T>>.isOutOfBounds(point: Point): Boolean {

    if (point.x < 0 || point.y < 0) {
        return true
    }

    if (point.y >= this.size) {
        return true
    }

    return point.x >= this[point.y].size
}


fun <T> List<List<T>>.enumeratePoints(): Sequence<Point> {
    return sequence {
        for (y in 0..<this@enumeratePoints.size) {          // This outer scope syntax is rather ugly
            for (x in 0..<this@enumeratePoints[y].size) {
                yield(Point(x, y))
            }
        }
    }
}


operator fun <T> List<List<T>>.get(point: Point): T {
    return this[point.y][point.x]
}


operator fun <T> MutableList<MutableList<T>>.set(point: Point, value: T): Unit {
    this[point.y][point.x] = value
}