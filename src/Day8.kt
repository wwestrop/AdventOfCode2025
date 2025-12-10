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

fun day8(rawInput: String): Long {
    val boxes = parseLinesIndexed(rawInput, ::parseLine)

    val distances = findBoxDistances(boxes)
        .sortedBy { it.second }
        .toList()
    val sortedDistances = distances
        .sortedBy { it.second }
        .take(10)
        //.take(1000)
        .map { it.first }
        .toList()

    val preferredCircuits = mutableSetOf<Int>()
    for ((box1, box2) in sortedDistances) {
        // connect the two boxes into the same circuit
        if (box2.circuit in preferredCircuits) {
            box1.circuit = box2.circuit
        } else {
            box2.circuit = box1.circuit
            preferredCircuits.add(box1.circuit)
        }
    }

    val uniqueCircuits = boxes.groupBy { it.circuit }

    return 0
}

fun findBoxDistances(boxes: List<JunctionBox>): Sequence<Pair<Pair<JunctionBox, JunctionBox>, Double>> {
    return sequence {
        for (i in boxes.count() - 1 downTo 0) {
            for (j in 0..<i) {
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