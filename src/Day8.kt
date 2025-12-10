import kotlin.math.max
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

    val distancesDebug = distances.sortedBy { it.second }.map { "\"${it.first.first.position}\", ${it.first.first.circuit}, \"${it.first.second.position}\", ${it.first.second.circuit}, ${it.second}" }
    val distancesDebugString = distancesDebug.joinToString("\n")

    val sortedDistances = distances
        .sortedBy { it.second }
        .take(10)
        //.take(1000)
        .map { it.first }
        .toList()

//    val circuitContents = sortedDistances.map { it.first.circuit }.toSet()      // TODO it's possible considering only the first n will leave me with an inaccurate result, but I'm going to a assume a long tail of circuits of 1 that make no difference. YOLO
    val uniqueCircuits = boxes
        .groupBy { it.circuit }
        .entries
        .associateBy(
            keySelector =  { it.key },
            valueTransform = { it.value.toMutableSet() })
    val rrrrr = uniqueCircuits[2]


    val preferredCircuits = mutableSetOf<Int>()
    for ((box1, box2) in sortedDistances) {
        // if both boxes are already in circuits, they all merge into the lower circuit
//        if (uniqueCircuits[box1.circuit]!!.size > 1 && uniqueCircuits[box2.circuit]!!.size > 1) {
//            // box2.circuit = box1.circuit
//            TODO
//        }

        // everything merges into the lower circuit number
        val (smaller, larger) =
            if (box1.circuit < box2.circuit) Pair(box1.circuit, box2.circuit)
            else Pair(box2.circuit, box1.circuit)

        val moving = uniqueCircuits[larger]!!
        for (box in moving) {
            box.circuit = smaller
        }
        uniqueCircuits[smaller]!!.addAll(moving)        // TODO no this doesn't work because I need to fiddle the circuit on all those I'm moving


        //uniqueCircuits.remove(larger)
        uniqueCircuits[larger]!!.clear()



        // otherwise, the box in the highest circuit
//        if (box2.circuit in preferredCircuits) {
//            box1.circuit = box2.circuit
//        } else {
//            box2.circuit = box1.circuit
//            preferredCircuits.add(box1.circuit)
//        }
    }
//
//    val uniqueCircuits = boxes.groupBy { it.circuit }

    return 0
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