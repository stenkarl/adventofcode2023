package day15

private val codes = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day15.txt").readLines().first().split(",")
private val boxes = Array<MutableList<Lens>>(256) { mutableListOf() }

private data class Lens(val label:String, val op:Char, val focal:Int = 0) {
    val hash = hash(label)

    override fun toString(): String {
        return "Lens($label, $op, $focal, $hash)"
    }
}


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
    val lenses = codes.map {
        if (it.contains('=')) {
            Lens(it.substring(0, it.length - 2), it[it.length - 2], it[it.length - 1].digitToInt())
        } else {
            Lens(it.substring(0, it.length - 1), it[it.length - 1])
        }
    }
    println(lenses)

    lenses.forEach { lens ->
        apply(lens)
    }
    printBoxes()

    val sum = boxes.mapIndexed { boxIndex, list ->
        list.mapIndexed { index, lens ->
            (boxIndex + 1) * (index + 1) * lens.focal
        }.sum()
    }.sum()

    println("Sum $sum")
}

private fun apply(lens:Lens) {
    val list = boxes[lens.hash]
    when (lens.op) {
        '-' -> list.removeIf { it.label == lens.label }
        '=' -> {
            val filtered = list.filter { it.label == lens.label }
            if (filtered.isNotEmpty()) {
                val first = filtered.first()
                val idx = list.indexOf(first)
                list.add(idx, lens)
                list.remove(first)
            } else {
                list.add(lens)
            }
        }


    }
}

private fun printBoxes() {
    boxes.forEachIndexed { index, list ->
        if (list.isNotEmpty()) println("$index: $list")
    }
}

private fun hash(str:String) =
    str.fold(0) { acc, ch ->
        (acc + ch.code) * 17 % 256
    }