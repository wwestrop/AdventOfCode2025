import kotlin.math.pow
import kotlin.math.sqrt

data class JunctionBox(val position: Triple<Int, Int, Int>, var circuit: Int)

private fun parseLine(i: Int, input: String): JunctionBox {
    val parts = input
        .trim()
        .split(",")
        .map { it.toInt() }

    return JunctionBox(position = Triple(parts[0], parts[1], parts[2]), circuit = i)
}

fun day8(rawInput: String): Int {
    val boxes = parseLinesIndexed(rawInput, ::parseLine)

    val sortedDistances = findBoxDistances(boxes)
        .sortedBy { it.second }
        .take(1000)
        //.take(10)
        .map { it.first }
        .toList()

    val uniqueCircuits = boxes
        .groupBy { it.circuit }
        .entries
        .associateBy(
            keySelector =  { it.key },
            valueTransform = { it.value.toMutableSet() })

    for ((box1, box2) in sortedDistances) {
        // everything merges into the lower circuit number
        val (smaller, larger) =
            if (box1.circuit < box2.circuit) Pair(box1.circuit, box2.circuit)
            else Pair(box2.circuit, box1.circuit)

        if (smaller != larger) {
            val moving = uniqueCircuits[larger]!!
            for (box in moving) {
                box.circuit = smaller
            }
            uniqueCircuits[smaller]!!.addAll(moving)
            uniqueCircuits[larger]!!.clear()
        }
    }

    return uniqueCircuits.entries
        .map { it.value.size }
        .sortedDescending()
        .take(3)
        .reduce { acc, v -> acc * v }
}

fun day8part2(rawInput: String): Int {
    val boxes = parseLinesIndexed(rawInput, ::parseLine)

    val sortedDistances = findBoxDistances(boxes)
        .sortedBy { it.second }
        .map { it.first }
        .toList()

    var circuitCount = boxes.size
    val uniqueCircuits = boxes
        .groupBy { it.circuit }
        .entries
        .associateBy(
            keySelector =  { it.key },
            valueTransform = { it.value.toMutableSet() })

    for ((box1, box2) in sortedDistances) {
        // everything merges into the lower circuit number
        val (smaller, larger) =
            if (box1.circuit < box2.circuit) Pair(box1.circuit, box2.circuit)
            else Pair(box2.circuit, box1.circuit)

        if (smaller != larger) {
            val moving = uniqueCircuits[larger]!!
            for (box in moving) {
                box.circuit = smaller
            }
            uniqueCircuits[smaller]!!.addAll(moving)
            uniqueCircuits[larger]!!.clear()
            circuitCount--

            if (circuitCount == 1) {
                return box1.position.first * box2.position.first
            }
        }
    }

    throw Exception("Should connect all the boxes before this happens")
}

fun findBoxDistances(boxes: List<JunctionBox>): Sequence<Pair<Pair<JunctionBox, JunctionBox>, Double>> {
    return sequence {
        for (i in 0..<boxes.count()) {
            for (j in 0..i - 1) {
                val box1 = boxes[i]
                val box2 = boxes[j]

                val xDist = Math.abs(box1.position.first - box2.position.first).toDouble()
                val yDist = Math.abs(box1.position.second - box2.position.second).toDouble()
                val zDist = Math.abs(box1.position.third - box2.position.third).toDouble()
                val dist = sqrt(xDist.pow(2.0) + yDist.pow(2.0) + zDist.pow(2.0))

                yield(Pair(Pair(box1, box2), dist))
            }
        }
    }
}