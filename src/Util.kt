import java.io.File


fun <TOutput> parseLines(input: String, parser: (String) -> TOutput): List<TOutput> {
    return input.lines()
        .filter { !it.trim().isEmpty() }
        //.map { it.trim() }
        .map(parser)
}


fun <TOutput> parseLinesIndexed(input: String, parser: (Int, String) -> TOutput): List<TOutput> {
    return input.lines()
        .filter { !it.trim().isEmpty() }
        //.map { it.trim() }
        .mapIndexed { i, s -> parser(i, s) }
}


fun <T> printGrid(
    grid: List<List<T>>,
    transformer: (T, Point) -> Char = { v, p -> v.toString()[0] },
    highlighter: (T, Point) -> Boolean = { v, p -> false }) {

    val ANSI_RESET = "\u001B[0m"
    val ANSI_RED = "\u001B[31m"

    println("---------------------------------------------")

    for (y in 0 ..<grid.size) {
        for (x in 0 ..<grid[y].size) {
            val transformed = transformer(grid[y][x], Point(x, y))
            if (highlighter(grid[y][x], Point(x, y))) {
                print("$ANSI_RED$transformed$ANSI_RESET ")
            }
            else {
                print("$transformed ")
            }
        }
        println()
    }

    println("---------------------------------------------")
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

    val input = file.readText() //.trim()
    val result = func(input)
    println("    ${filename.padEnd(6)}   -> $result")
}