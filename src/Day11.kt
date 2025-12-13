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

