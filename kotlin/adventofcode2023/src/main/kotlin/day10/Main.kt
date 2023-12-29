package day10

typealias Point = Pair<Int, Int>

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

fun Point.toNatural(): String =
    "(${this.first + 1}, ${this.second + 1})"

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day10.txt").readLines()

fun main() {
    //part1()
    part2()
}

private fun part1() {
    val start = findStart()
    var steps = 1
    println("Start ${start.toNatural()}")
    val firstStep = Point(start.first + 1, start.second)
    println("First step: ${firstStep.toNatural()} ${charAt(firstStep)}")
    var next = nextStep(firstStep, Direction.DOWN)
    while (charAt(next.first) != 'S') {
    //while (steps < 7) {
        println("nextStep ${next.first.toNatural()} ${charAt(next.first)} ${next.second} steps: $steps")
        next = nextStep(next.first, next.second)
        steps++
    }
    println("Steps to wrap around $steps")
    println("Furthest point ${steps / 2 + 1}")
}

private fun nextStep(point: Point, dir: Direction): Pair<Point, Direction> {
    //println("begin nextStep ${point.toNatural()} ${charAt(point)} $dir")
    return when (charAt(point)) {
        '|' -> when (dir) {
            Direction.DOWN -> Pair(Point(point.first + 1, point.second), Direction.DOWN)
            else -> Pair(Point(point.first - 1, point.second), Direction.UP)
        }

        '-' -> when (dir) {
            Direction.LEFT -> Pair(Point(point.first, point.second - 1), Direction.LEFT)
            else -> Pair(Point(point.first, point.second + 1), Direction.RIGHT)
        }

        '7' -> when (dir) {
            Direction.RIGHT -> Pair(Point(point.first + 1, point.second), Direction.DOWN)
            else -> Pair(Point(point.first, point.second - 1), Direction.LEFT)
        }

        'J' -> when (dir) {
            Direction.DOWN -> Pair(Point(point.first, point.second - 1), Direction.LEFT)
            else -> Pair(Point(point.first - 1, point.second), Direction.UP)
        }

        'L' -> when (dir) {
            Direction.DOWN -> Pair(Point(point.first, point.second + 1), Direction.RIGHT)
            else -> Pair(Point(point.first - 1, point.second), Direction.UP)
        }

        'F' -> when (dir) {
            Direction.UP -> Pair(Point(point.first, point.second + 1), Direction.RIGHT)
            else -> Pair(Point(point.first + 1, point.second), Direction.DOWN)
        }

        else -> throw IllegalStateException("Unexpected char ${charAt(point)}")
    }
}

private fun charAt(point: Point) =
    lines[point.first][point.second]

private fun findStart(): Point {
    for (i in lines.indices) {
        for (j in lines[i].indices) {
            if (lines[i][j] == 'S') {
                return Point(i, j)
            }
        }
    }
    throw IllegalStateException("Can't find start")
}

private fun part2() {
    val loop = traceLoop()
    println(loop)
    val total = findNumberInLoop(loop)
    println("Number in loop: $total")
}

private fun findNumberInLoop(loop: List<Point>): Int {
    var count = 0
    for (row in lines.indices) {
        for (col in lines[row].indices) {
            if (isIn(Point(row, col), loop)) {
                count++
            }
        }
    }
    return count
}

private fun isIn(point: Point, loop: List<Point>): Boolean {
    if (loop.contains(point)) {
        return false
    }
    val rightCast = castRight(point, loop)
    val leftCast = castLeft(point, loop)
    val rightCastIsIn = rightCast % 2 == 1
    val leftCastIsIn = leftCast % 2 == 1
    if (rightCastIsIn != leftCastIsIn) {
        throw IllegalStateException("Left cast ($leftCast) does not equal right cast ($rightCast) for ${point.toNatural()}")
    }

    if (rightCastIsIn) {
        println("${point.toNatural()} ${charAt(point)} is in")
    }

    return rightCastIsIn
}

private fun castRight(point: Point, loop: List<Point>): Int {
    var num = 0
    var l7 = ' '
    var fJ = ' '
    for (col in point.second + 1..<lines.first().length) {
        val curPoint = Point(point.first, col)
        if (!loop.contains(curPoint)) {
            continue
        }
        val c = charAt(curPoint)
        num += when (c) {
            '|' -> 1
            'L' -> { l7 = 'L'; 0 }
            '7' -> if (l7 == 'L') { l7 = ' '; 1 } else { fJ = ' '; 0 }
            'F' -> { fJ = 'F'; 0 }
            //'S' -> { fJ = 'F'; 0 }
            'J' -> if (fJ == 'F') { fJ = ' '; 1} else { l7 = ' '; 0 }
            else -> 0
        }
    }

    return num
}

private fun castLeft(point: Point, loop: List<Point>): Int {
    var num = 0
    var l7 = ' '
    var fJ = ' '
    for (col in point.second - 1 downTo 0) {
        val curPoint = Point(point.first, col)
        if (!loop.contains(curPoint)) {
            continue
        }
        val c = charAt(curPoint)
        num += when (c) {
            '|' -> 1
            '7' -> { l7 = '7'; 0 }
            'L' -> if (l7 == '7') { l7 = ' '; 1 } else { fJ = ' '; 0 }
            'J' -> { fJ = 'J'; 0 }
            'F' -> if (fJ == 'J') { fJ = ' '; 1} else { l7 = ' '; 0 }
            //'S' -> if (fJ == 'J') { fJ = ' '; 1} else 0
            else -> 0
        }
    }

    return num
}

private fun traceLoop(): List<Point> {
    val start = Point(120, 110)//findStart()
    val points = mutableListOf(start)
    println("Start ${start.toNatural()}")
    val firstStep = Point(start.first + 1, start.second)
    points.add(firstStep)
    //println("First step: ${firstStep.toNatural()} ${charAt(firstStep)}")
    var next = nextStep(firstStep, Direction.DOWN)
    while (next.first != start) {
        points.add(next.first)
        //println("nextStep ${next.first.toNatural()} ${charAt(next.first)} ${next.second}")
        next = nextStep(next.first, next.second)
    }
    return points.toList()
}