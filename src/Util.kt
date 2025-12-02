import java.io.File
import java.util.Optional


fun <TOutput> parseLines(input: String, parser: (String) -> TOutput): List<TOutput> {
    return input.lines()
        .filter { !it.trim().isEmpty() }
        .map(parser)
}

fun <TOutput> day(num: Int, func: (String) -> TOutput) {
    day(num, func, Optional.empty())
}

fun <TOutput> day(num: Int, func1: (String) -> TOutput, func2: Optional<(String) -> TOutput>) {
    println("Day $num:")

    println("  Part 1:")
    doday(num, "sample", func1)
    doday(num, "full", func1)

    func2.ifPresent {
        println("  Part 2:")
        doday(num, "sample", it)
        doday(num, "full", it)
    }

    println("------------------------------------------\n")
}

private fun <TOutput> doday(day: Int, filename: String, func: (String) -> TOutput) {
    val dir = "out/production/AdventOfCode2025/inputs/day${day.toString().padStart(2, '0')}"
    val file = File("$dir/$filename")


    if (!file.exists()) {
        println("    ${filename.padEnd(6)}   -> (not completed)")
        return
    }

    val result = func(file.readText())
    println("    ${filename.padEnd(6)}   -> $result")
}