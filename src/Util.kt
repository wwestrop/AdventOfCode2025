import java.io.File


fun <TOutput> parseLines(input: String, parser: (String) -> TOutput): List<TOutput> {
    return input.lines()
        .filter { !it.trim().isEmpty() }
        .map(parser)
}


fun <TOutput> day(num: Int, func1: (String) -> TOutput, func2: ((String) -> TOutput)? = null) {
    println("Day $num:")

    println("  Part 1:")
    doDay(num, "sample", func1)
    doDay(num, "full", func1)
    
    if (func2 != null) {
        println("  Part 2:")
        doDay(num, "sample", func2)
        doDay(num, "full", func2)
    }

    println("------------------------------------------\n")
}

private fun <TOutput> doDay(day: Int, filename: String, func: (String) -> TOutput) {
    val dir = "out/production/AdventOfCode2025/inputs/day${day.toString().padStart(2, '0')}"
    val file = File("$dir/$filename")


    if (!file.exists()) {
        println("    ${filename.padEnd(6)}   -> (not completed)")
        return
    }

    val input = file.readText().trim()
    val result = func(input)
    println("    ${filename.padEnd(6)}   -> $result")
}