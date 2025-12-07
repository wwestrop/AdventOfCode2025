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
        .map { it.toMutableList() }
        .toMutableList()

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
