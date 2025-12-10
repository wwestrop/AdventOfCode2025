enum class CellType {
    Start,
    Splitter,
    Space,
}

private fun parseLine(input: String): List<CellType> {
    return input.map {
        when (it) {
            'S' -> CellType.Start
            '^' -> CellType.Splitter
            '.' -> CellType.Space
            else -> error("Unknown cell type '$it'")
        }
    }
}

fun day7(rawInput: String): Long {
    val grid = parseLines(rawInput, ::parseLine)

    var accumulator = 0L
    val startPoint = grid.enumeratePoints().first { grid[it] == CellType.Start }

    val downVector = Point(0, 1)
    var beamFrontier = setOf(startPoint + downVector)
    val allBeams = beamFrontier.toMutableSet()
    while (beamFrontier.any()) {
        val newBeams = mutableSetOf<Point>()

        for (beam in beamFrontier) {
            if (grid[beam] == CellType.Splitter) {
                // spawn the new beams
                val leftBeam = beam + Point(-1, 0)
                if (!grid.isOutOfBounds(leftBeam) && !allBeams.contains(leftBeam)) {    // don't emit another beam into a beam passing parallel (since the "frontier" for that path has already moved on)
                    newBeams.add(leftBeam)
                }

                val rightBeam = beam + Point(1, 0)
                if (!grid.isOutOfBounds(rightBeam) && !allBeams.contains(rightBeam)) {
                    newBeams.add(rightBeam)
                }

                accumulator++
            }
            else {
                val beamContinuation = beam + downVector
                if (!grid.isOutOfBounds(beamContinuation) && !allBeams.contains(beamContinuation)) {
                    newBeams.add(beamContinuation)
                }
            }
        }

        beamFrontier = newBeams.toMutableSet()
        allBeams.addAll(newBeams)
    }

    return accumulator
}


fun day7Part2(rawInput: String): Long {
    val grid = parseLines(rawInput, ::parseLine)

    val startPoint = grid.enumeratePoints().first { grid[it] == CellType.Start }

    return countUniquePaths(startPoint, grid)
}

fun countUniquePaths(from: Point, grid: List<List<CellType>>): Long {
    val downVector = Point(0, 1)

    fun countUniquePaths(from: Point, grid: List<List<CellType>>, memo: MutableList<MutableList<Long>>): Long {
        if (grid.isOutOfBounds(from)) {
            return 1
        }

        if (memo[from] != 0L) {
            return memo[from]
        }

        if (grid[from] == CellType.Splitter) {
            val leftBeam = from + Point(-1, 0)
            val rightBeam = from + Point(1, 0)

            memo[from] = countUniquePaths(leftBeam, grid, memo) + countUniquePaths(rightBeam, grid, memo)
            return memo[from]
        }
        else {
            val downBeam = from + downVector

            memo[from] = countUniquePaths(downBeam, grid, memo)
            return memo[from]
        }
    }

    val memo = grid.map { it.map { _ -> 0L }.toMutableList() }.toMutableList()
    return countUniquePaths(from, grid, memo)
}
