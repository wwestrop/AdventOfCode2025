data class Point(val x: Int, val y: Int) {
    operator fun plus (p: Point): Point {
        return Point(this.x + p.x, this.y + p.y)
    }
}