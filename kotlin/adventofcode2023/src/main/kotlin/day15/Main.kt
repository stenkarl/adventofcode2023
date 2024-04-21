package day15

val codes = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day15sample.txt").readLines().first().split(",")

fun main() {
    println(codes)
    //println("HASH: ${hash("HASH")}")
    //part1()
    part2()
}

private fun part1() {
    val sum = codes.map {
        hash(it)
    }.toIntArray().sum()

    println("Sum $sum")
}

private fun part2() {
    println("")
}

private fun hash(str:String) =
    str.fold(0) { acc, ch ->
        //println("$ch, acc: $acc, code: ${ch.code}")
        (acc + ch.code) * 17 % 256
    }