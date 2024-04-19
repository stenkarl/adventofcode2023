package day14

typealias Point = Pair<Int, Int>

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day14.txt").readLines()
val NUM_ROWS = lines.size
val NUM_COLS = lines.first().length

val map = buildMap()

val cache = mutableMapOf<String, Int>()

fun main() {
    println(map)
    //part1()
    part2()
}

private fun buildMap():MutableMap<Point, Char> {
    val m = mutableMapOf<Point, Char>()
    for (row in lines.indices) {
        for (col in lines[row].indices) {
            m[Point(row, col)] = lines[row][col]
        }
    }

    return m
}

private fun part1() {
    for (row in 0..<NUM_ROWS) {
        for (col in 0 ..<NUM_COLS) {
            if (map[Point(row, col)] == 'O') {
                moveRockNorth(Point(row, col))
            }
        }
    }

    prettyPrint()
    println("Sum ${calcLoad()}")
}

private fun moveRockNorth(point:Point) {
    var curRow = point.first - 1
    while (true) {
        if (curRow >= 0) {
            val p = Point(curRow, point.second)
            if (map[p] == '.') {
                map[p] = 'O'
                map[Point(curRow + 1, point.second)] = '.'
            } else {
                break
            }
        } else {
            break
        }
        curRow--
    }
}

private fun moveRockSouth(point:Point) {
    var curRow = point.first + 1
    while (true) {
        if (curRow < NUM_ROWS) {
            val p = Point(curRow, point.second)
            if (map[p] == '.') {
                map[p] = 'O'
                map[Point(curRow - 1, point.second)] = '.'
            } else {
                break
            }
        } else {
            break
        }
        curRow++
    }
}

private fun moveRockWest(point:Point) {
    var curCol = point.second - 1
    while (true) {
        if (curCol >= 0) {
            val p = Point(point.first, curCol)
            if (map[p] == '.') {
                map[p] = 'O'
                map[Point(point.first, curCol + 1)] = '.'
            } else {
                break
            }
        } else {
            break
        }
        curCol--
    }
}

private fun moveRockEast(point:Point) {
    var curCol = point.second + 1
    while (true) {
        if (curCol < NUM_COLS) {
            val p = Point(point.first, curCol)
            if (map[p] == '.') {
                map[p] = 'O'
                map[Point(point.first, curCol - 1)] = '.'
            } else {
                break
            }
        } else {
            break
        }
        curCol++
    }
}

private fun prettyPrint() {
    for (row in 0..<NUM_ROWS) {
        val builder = StringBuilder()
        for (col in 0..<NUM_COLS) {
            builder.append(map[Point(row, col)])
        }
        println(builder.toString())
    }
}

private fun calcLoad():Int {
    var sum = 0
    for (row in 0..<NUM_ROWS) {
        for (col in 0..<NUM_COLS) {
            if (map[Point(row, col)] == 'O') {
                sum += NUM_ROWS - row
            }
        }
    }
    return sum
}

private fun part2() {
    var index = 0
    var firstOfCycle = ""
    var cycleCount = 0
    var cycleIndexStart = 0
    while (true) {
        val curCycle = cycle()
        println("$index Load: ${calcLoad()}")
        if (cache.containsKey(curCycle)) {
            if (firstOfCycle == "") {
                firstOfCycle = curCycle
                cycleIndexStart = index
            } else if (firstOfCycle == curCycle) {
                break
            }
            else {
                cycleCount++
            }
            //println("Found cycle at $index with cached load ${cache[curCycle]}, ${calcLoad()}")
            //break
        } else {
            cache[curCycle] = calcLoad()
        }
        index++
        //if (index > 30) {
        //    break
        //}
    }
    cycleCount++
    println("end at $index, cycle length: $cycleCount")

    println("Found cycle at $cycleIndexStart")
    val remainder = (1000000000 - cycleIndexStart) % cycleCount
    println("Can skip ${(1000000000 - cycleIndexStart) / cycleCount} cycles. Only need to do $remainder")
    for (i in 1..<remainder) {
        cycle()
        println("$i Load: ${calcLoad()}")
    }
    prettyPrint()
    println("Sum ${calcLoad()}")

}

private fun cycle():String {
    for (row in 0..<NUM_ROWS) {
        for (col in 0 ..<NUM_COLS) {
            if (map[Point(row, col)] == 'O') {
                moveRockNorth(Point(row, col))
            }
        }
    }

    for (row in 0..<NUM_ROWS) {
        for (col in 0 ..<NUM_COLS) {
            if (map[Point(row, col)] == 'O') {
                moveRockWest(Point(row, col))
            }
        }
    }

    for (row in NUM_ROWS - 1 downTo 0) {
        for (col in 0 ..<NUM_COLS) {
            if (map[Point(row, col)] == 'O') {
                moveRockSouth(Point(row, col))
            }
        }
    }

    for (row in 0..<NUM_ROWS) {
        for (col in NUM_COLS - 1 downTo 0) {
            if (map[Point(row, col)] == 'O') {
                moveRockEast(Point(row, col))
            }
        }
    }
    return mapToString()
}

private fun mapToString():String {
    val builder = StringBuilder()
    for (row in 0..<NUM_ROWS) {
        for (col in 0..<NUM_COLS) {
            builder.append(map[Point(row, col)])
        }
    }
    return builder.toString()
}