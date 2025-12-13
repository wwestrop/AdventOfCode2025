data class Vertex(val name: String, val outEdges: Set<String>)

private fun parseLine(input: String): Vertex {
    val parts = input
        .trim()
        .split(":")
        .map { it.trim() }

    val vertexLabel = parts[0]
    val outEdges = parts[1]
        .split(" ")
        .map { it.trim() }
        .toSet()

    return Vertex(vertexLabel, outEdges)
}

fun day11(rawInput: String): Long {

    val vertices = parseLines(rawInput, ::parseLine)
        .associateBy(
            keySelector = { it.name },
            valueTransform = { it.outEdges })

    return countUniquePaths("you", "out", vertices)
}


fun day11part2(rawInput: String): Long {

    val vertices = parseLines(rawInput, ::parseLine)
        .associateBy(
            keySelector = { it.name },
            valueTransform = { it.outEdges })

    val svrToDac = countUniquePaths2("svr", "dac", vertices)
    val svrToFft = countUniquePaths2("svr", "fft", vertices)

    val dacToOutViaFft = countUniquePaths2Via("dac", "out", waypoints = setOf("fft"), vertices)
    val fftToOutViaDac = countUniquePaths2Via("fft", "out" ,  waypoints = setOf("dac"), vertices)

    return if (dacToOutViaFft == 0L) {
        // srv -> fft -> dac -> out is the order
        svrToFft * fftToOutViaDac
    } else {
        // srv -> dac -> fft -> out is the order
        svrToDac * dacToOutViaFft
    }
}


fun countUniquePaths(fromVertex: String, toVertex: String, graph: Map<String, Set<String>>): Long {

    fun countUniquePaths(fromVertex: String, toVertex: String, graph: Map<String, Set<String>>, memo: MutableMap<String, Long>): Long {
        if (fromVertex == toVertex) {
            return 1    // Should probably be 0 really, if we're already at the end
        }

        if (memo[fromVertex] != Long.MIN_VALUE) {
            return memo[fromVertex]!!       // TODO why is this `Long?` when day 7 isn't???
        }

        var childPaths = 0L
        for (outEdgeTo in graph[fromVertex]!!) {
            childPaths += countUniquePaths(outEdgeTo, toVertex, graph, memo)
        }

        memo[fromVertex] = childPaths
        return memo[fromVertex]!!       // TODO why is this `Long?` when day 7 isn't???
    }

    val memo = graph.entries
        .associateBy(
            keySelector = { it.key },
            valueTransform = { Long.MIN_VALUE }
        ).toMutableMap()

    return countUniquePaths(fromVertex, toVertex, graph, memo)
}

fun countUniquePaths2(fromVertex: String, toVertex: String, graph: Map<String, Set<String>>): Long {

    fun countUniquePaths2(fromVertex: String, toVertex: String, graph: Map<String, Set<String>>, memo: MutableMap<String, Long>): Long {
        if (fromVertex !in graph) {
            return 0L               // a hack to make the part 2 work, but makes it maybe less general
        }
        if (fromVertex == toVertex) {
            return 1    // Should probably be 0 really, if we're already at the end
        }

        if (memo[fromVertex] != Long.MIN_VALUE) {
            return memo[fromVertex]!!       // TODO why is this `Long?` when day 7 isn't???
        }

        var childPaths = 0L
        for (outEdgeTo in graph[fromVertex]!!) {

            childPaths += countUniquePaths2(outEdgeTo, toVertex, graph, memo)
        }

        memo[fromVertex] = childPaths
        return memo[fromVertex]!!       // TODO why is this `Long?` when day 7 isn't???
    }

    val memo = graph.entries
        .associateBy(
            keySelector = { it.key },
            valueTransform = { Long.MIN_VALUE }
        ).toMutableMap()

    return countUniquePaths2(fromVertex, toVertex, graph, memo)
}


fun countUniquePaths2Via(fromVertex: String, toVertex: String, waypoints: Set<String>, graph: Map<String, Set<String>>): Long {

    fun countUniquePaths2(fromVertex: String, toVertex: String, visitedWaypoints: Set<String>, graph: Map<String, Set<String>>, memo: MutableMap<String, Long>): Long {
        if (fromVertex == toVertex) {
            return if (visitedWaypoints.containsAll(waypoints)) 1 else Long.MIN_VALUE
        }

        if (memo[fromVertex] != Long.MIN_VALUE && memo[fromVertex] != 0L) {     // 0 check probably won't work, what about graphs with dead ends?
            return memo[fromVertex]!!       // TODO why is this `Long?` when day 7 isn't???
        }

        var childPaths = 0L
        val visitedWaypointsAsOfThisVertex = if (fromVertex in waypoints) visitedWaypoints + fromVertex else visitedWaypoints
        for (outEdgeTo in graph[fromVertex]!!) {
            childPaths += countUniquePaths2(outEdgeTo, toVertex, visitedWaypointsAsOfThisVertex, graph, memo)
        }

        memo[fromVertex] = childPaths
        return memo[fromVertex]!!       // TODO why is this `Long?` when day 7 isn't???
    }

    val memo = graph.entries
        .associateBy(
            keySelector = { it.key },
            valueTransform = { Long.MIN_VALUE }
        ).toMutableMap()

    return countUniquePaths2(fromVertex, toVertex, visitedWaypoints=setOf<String>(), graph, memo)
}
