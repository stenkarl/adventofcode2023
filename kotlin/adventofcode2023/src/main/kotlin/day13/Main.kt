package day13

typealias Grid = List<String>
typealias Combo = Pair<Char, Int>

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day13.txt").readLines()

val grids = buildGrids()
val originalReflections = mutableMapOf<Int, Combo>()

fun main() {
    //println(grids)
    //part1()
    //part1()
    part2()
}

private fun part1() {
    val sum = grids.sumOf { findReflection(it, 0) }

    println("Sum $sum")
}

private fun part2() {
    grids.forEachIndexed { index, grid -> findReflection(grid, index) }
    println(originalReflections)

    val sum = grids.mapIndexed { index, grid -> findSmudge(grid, index) }.sum()
    println("Part 2: $sum")
}

private fun findReflection(grid:Grid, index:Int): Int {
    val vert = findVertical(grid, index)
    return if (vert > 0) {
        vert
    } else {
        findHorizontal(grid, index)
    }
}

private fun findVertical(grid:Grid, index:Int): Int {
    for (i in 0..<grid.first().length - 1) {
        if (testColumn(grid, i)) {
            if (!originalReflections.containsKey(index)) {
                originalReflections[index] = Combo('v', i)
            } else if (originalReflections[index] == Combo('v', i)) {
                println("Found original vertical reflection ${originalReflections[index]}")
                continue
            }
            return i + 1
        }
    }
    return 0
}

private fun testColumn(grid:Grid, i:Int):Boolean {
    //println("testColumn $i")
    var first = i
    var second = i + 1
    while (first >= 0 && second < grid.first().length) {
        val col1 = getCol(grid, first)
        val col2 = getCol(grid, second)
        if (col1 != col2) {
            //println("first  $first: $col1")
            //println("second $second: $col2")
            return false
        }
        first -= 1
        second += 1
    }
    return true
}

private fun getCol(grid:Grid, i:Int) = grid.map { it[i]}

private fun findHorizontal(grid:Grid, index:Int): Int {
    for (i in 0..<grid.size - 1) {
        if (testRow(grid, i)) {
            if (!originalReflections.containsKey(index)) {
                originalReflections[index] = Combo('h', i)
            } else if (originalReflections[index] == Combo('h', i)){
                println("Found original horizontal reflection ${originalReflections[index]}")
                continue
            }
            return (i + 1) * 100
        }
    }
    return 0
    //println("Couldn't find reflection for: $grid")
    //error("Shouldn't reach here")
}

private fun testRow(grid:Grid, i:Int):Boolean {
    var first = i
    var second = i + 1
    //println("testRow $i")
    while (first >= 0 && second < grid.size) {
        val row1 = grid[first]
        val row2 = grid[second]
        //println(row1)
        //println(row2)
        //println("equal: ${row1 == row2}")
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

private fun findSmudge(grid: Grid, index: Int): Int {
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            val testGrid = makeGrid(grid, row, col)
            //println("Testing $row $col $grid || $testGrid")
            val ret = findReflection(testGrid, index)
            if (ret > 0) {
                return ret
            }
        }
    }
    error("Couldn't find smudge for $index $grid")
}

private fun makeGrid(grid: Grid, row: Int, col: Int): Grid {
    val list = mutableListOf<String>()
    for (r in grid.indices) {
        if (r == row) {
            val builder = StringBuilder(grid[r])
            builder.setCharAt(col, if (builder[col] == '.') '#' else '.')
            list.add(builder.toString())
        } else {
            list.add(grid[r])
        }
    }
    return list.toList()
}