package day9

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day9sample.txt").readLines()
val lists = lines.map { nums ->
    nums.split(" ").map {
        it.toInt()
    }
}


fun main() {
    println(lists)
    part2()
}

fun part1() {
    val sum = lists.fold(0) { acc, ints -> acc + processList(ints) }
    println("Sum $sum")
}

fun part2() {
    val sum = lists.fold(0) { acc, ints -> acc + processListFromStart(ints) }
    println("Sum $sum")
}

fun processList(list: List<Int>): Int {
    val diff = diff(list)
    if (diff.all { it == 0 }) return list.last()
    val next = list.last() + processList(diff)
    println("$list $diff $next")
    return next
}

fun processListFromStart(list: List<Int>): Int {
    val diff = diff(list)
    if (diff.all { it == 0 }) return list.first()
    val next = list.first() - processListFromStart(diff)
    println("$list $diff $next")
    return next
}

fun diff(list: List<Int>): List<Int> =
    list.zip(list.subList(1, list.size)).map {
        it.second - it.first
    }