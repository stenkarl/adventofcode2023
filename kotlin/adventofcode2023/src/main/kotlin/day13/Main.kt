package day13

typealias Grid = List<String>

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day13.txt").readLines()

val grids = buildGrids()

fun main() {
    //println(grids)
    part1()
}

private fun part1() {
    val sum = grids.sumOf { findReflection(it) }

    println("Sum $sum")
}

private fun findReflection(grid:Grid): Int {
    val vert = findVertical(grid)
    return if (vert > 0) {
        vert
    } else {
        findHorizontal(grid)
    }
}

private fun findVertical(grid:Grid): Int {
    for (i in 0..<grid.first().length - 1) {
        if (testColumn(grid, i)) {
            return i + 1
        }
    }
    return 0
}

private fun testColumn(grid:Grid, i:Int):Boolean {
    println("testColumn $i")
    var first = i
    var second = i + 1
    while (first >= 0 && second < grid.first().length) {
        val col1 = getCol(grid, first)
        val col2 = getCol(grid, second)
        if (col1 != col2) {
            println("first  $first: $col1")
            println("second $second: $col2")
            return false
        }
        first -= 1
        second += 1
    }
    return true
}

private fun getCol(grid:Grid, i:Int) = grid.map { it[i]}

private fun findHorizontal(grid:Grid): Int {
    for (i in 0..<grid.size - 1) {
        if (testRow(grid, i)) {
            return (i + 1) * 100
        }
    }
    println("Couldn't find reflection for: $grid")
    error("Shouldn't reach here")
}

private fun testRow(grid:Grid, i:Int):Boolean {
    var first = i
    var second = i + 1
    while (first >= 0 && second < grid.size) {
        val row1 = grid[first]
        val row2 = grid[second]
        if (row1 != row2) {
            return false
        }
        first -= 1
        second += 1
    }

    return true
}

private fun buildGrids():List<Grid> {
    val list = mutableListOf<Grid>()
    var grid = mutableListOf<String>()
    for (line in lines) {
        if (line == "") {
            list.add(grid)
            grid = mutableListOf()
        } else {
            grid.add(line)
        }
    }
    list.add(grid)
    return list.toList()
}