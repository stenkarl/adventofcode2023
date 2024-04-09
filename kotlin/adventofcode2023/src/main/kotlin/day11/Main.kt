package day11

import kotlin.math.abs

typealias Point = Pair<Long, Long>
typealias Combo = Pair<Int, Int>

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day11.txt").readLines()
//val galaxy = expandGalaxy()
val positions = buildPositions()
val combinations = buildCombos()

fun main() {
    println(lines)
    //println(galaxy)

    part2()
}

/*
fun part1() {
    println(positions)
    println("positions size ${positions.size}")
    println(combinations)
    println("combos ${combinations.size}")
    val distances = combinations.map {
        val first = positions[it.first]
        val second = positions[it.second]

        abs(first.first - second.first) + Math.abs(first.second - second.second)
    }
    println(distances)
    println("Sum ${distances.sum()}")
}
*/

fun part2() {
    println(positions)
    println("positions size ${positions.size}")
    //println(combinations)
    println("combos ${combinations.size}")
    val distances = combinations.map {
        val first = positions[it.first]
        val second = positions[it.second]

        abs(first.first - second.first) + Math.abs(first.second - second.second)
    }
    println(distances)
    println("Sum ${distances.sum()}")
}

fun expandGalaxy():List<List<Char>> {
    val list = mutableListOf<MutableList<Char>>()
    val emptyCols = emptyColumns()
    val totalCols = emptyCols.size + lines.first().length
    println("emptyCols: ${emptyCols.size}")
    for (row in lines.indices) {
        val curRow = mutableListOf<Char>()
        for (col in lines.first().indices) {
            curRow.add(lines[row][col])
            if (emptyCols.contains(col)) {
                curRow.add('.')
            }
        }
        list.add(curRow)
        if (lines[row].all { it == '.' }) {
            list.add(MutableList(totalCols) { '.' })
        }
    }
    println("Galaxy size: ${list.size}")
    return list.toList()
}

fun emptyColumns(): List<Int> {
    val list = mutableListOf<Int>()
    for (col in lines.first().indices) {
        var isEmpty = true
        for (row in lines.indices) {
            if (lines[row][col] == '#') {
                isEmpty = false
                break
            }
        }
        if (isEmpty) {
            list.add(col)
        }
    }
    return list.toList()
}

fun emptyRows(): List<Int> {
    val list = mutableListOf<Int>()
    for (row in lines.indices) {
        var isEmpty = true
        for (col in lines[row].indices) {
            if (lines[row][col] == '#') {
                isEmpty = false
                break
            }
        }
        if (isEmpty) {
            list.add(row)
        }
    }
    return list.toList()
}

/*
fun buildPositions(): List<Point> {
    val list = mutableListOf<Point>()
    for (row in galaxy.indices) {
        for (col in galaxy[row].indices) {
            if (galaxy[row][col] == '#') {
                list.add(Point(row, col))
            }
        }
    }
    println("Positions: ${list.size}")
    return list.toList()
}
*/

fun buildPositions(): List<Point> {
    val list = mutableListOf<Point>()
    for (row in lines.indices) {
        for (col in lines[row].indices) {
            if (lines[row][col] == '#') {
                list.add(mapToExpandedPoint(row, col))
            }
        }
    }
    println("Positions: ${list.size}")
    return list.toList()
}

fun mapToExpandedPoint(row: Int, col: Int): Point {
    val numExpandedRows = emptyRows().filter { it <= row }.size
    val numExpandedCols = emptyColumns().filter { it <= col }.size

    val multiplier = 999999L
    val expandedRows = row + (numExpandedRows * multiplier)
    val expandedCols = col + (numExpandedCols * multiplier)

    println("Expanded point $row -> $expandedRows, $col -> $expandedCols")

    return Point(expandedRows, expandedCols)
}

fun buildCombos(): List<Combo> {
    val list = mutableListOf<Combo>()
    for (i in positions.indices) {
        for (j in positions.indices) {
            if (i != j) {
                if (!list.contains(Combo(i, j)) && !list.contains(Combo(j, i)))
                list.add(Combo(i, j))
            }
        }
    }
    println("Combos size: ${list.size}")
    return list.toList()
}