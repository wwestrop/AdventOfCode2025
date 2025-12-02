import java.io.File
import java.util.Optional


fun <TOutput> parseLines(input: String, parser: (String) -> TOutput): List<TOutput> {
    return input.lines()
        .filter { !it.trim().isEmpty() }
        .map(parser)
}

fun <TOutput> day(num: Int, func1: (String) -> TOutput, func2: (String) -> TOutput) {
    // TODO why do I need this overload, why won't it cast automatically to Optional?
    // yes it would weaken the type guarantees, but it doesn't guarantee anything it couldn't prove
    day(num, func1, Optional.of(func2))
}

fun <TOutput> day(num: Int, func1: (String) -> TOutput, func2: Optional<(String) -> TOutput> = Optional.empty()) {
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

    val input = file.readText().trim()
    val result = func(input)
    println("    ${filename.padEnd(6)}   -> $result")
}