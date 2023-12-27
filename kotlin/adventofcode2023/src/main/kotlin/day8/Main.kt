package day8

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day8.txt").readLines()
val moves = lines.first()

fun main() {
    println(lines)
    println(moves)

    //part1(buildMap())
    part2(buildMap())
}

fun buildMap(): Map<String, Pair<String, String>> =
    lines.subList(2, lines.size).map {
        val split = it.split(" = ")
        val pair = split[1].substring(1, split[1].length - 1).split(", ")
        split[0] to Pair(pair[0], pair[1])
    }.toMap()

fun part1(map: Map<String, Pair<String, String>>) {
    println(map)

    var nextLocation = "AAA"
    var count = 0
    var index = 0
    while (nextLocation != "ZZZ") {
        val dir = moves[index]
        val curPair = map[nextLocation]
        if (curPair != null) {
            nextLocation = if (dir == 'L') curPair.first else curPair.second
            println("nextLocations = $nextLocation")
        } else {
            println("Unexpected empty mapping for $nextLocation")
        }
        index = if (index == moves.length - 1) {
            println("resetting index $index ${moves.length}")
            0
        } else {
            index + 1
        }
        count++
    }
    println ("Found $nextLocation in $count steps")
}

fun part2(map: Map<String, Pair<String, String>>) {
    println(map)

    var list = map.keys.filter { it.endsWith("A") }.toList()
    println(list)
    var count = 0L
    var index = 0
    var done = false
    val zMap = mutableMapOf<String, Long>()
    while (!done) {
        val dir = moves[index]
        list = list.map {
            val curPair = map[it]
            if (dir == 'L') curPair!!.first else curPair!!.second
        }
        index = if (index == moves.length - 1) {
            //println("resetting index $index ${moves.length}")
            0
        } else {
            index + 1
        }
        count++
        for (item in list) {
            if (item.endsWith("Z") && !zMap.containsKey(item)) {
                zMap[item] = count
                println("Found end for $item $count")
                done = zMap.size.equals(list.size)
            }
        }
        if (done) {
            println("Finished after $count $map")
        }
    }
    val product = zMap.values.fold(1L) { acc, i -> i * acc }
    println(zMap.values)
    println ("Found $list in $count steps $product")
}