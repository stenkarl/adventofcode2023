package day12

import kotlin.math.pow


val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day12.txt").readLines()

fun main() {
    println(lines)
    part1()
}

fun part1() {
    val sum = lines.map { processLine(it) }.toIntArray().sum()
    println("Sum: $sum")
}

fun processLine(line:String):Int {
    val count = line.filter { it == '?' }.length
    if (count >= 32) {
        error("Count $count exceeds data size")
    }
    val combos = Integer.valueOf(2).toDouble().pow(count.toDouble()).toInt()
    println("$line Placeholders: $count, combos: $combos")
    val tokens = line.split(" ")
    val arrangement = Arrangement(tokens[0], tokens[1].split(",").map {
        Integer.valueOf(it)
    })
    val numValid = (0..< combos).map { num ->
        if (arrangement.isValid(Integer.toBinaryString(num).padStart(count, '0'))) 1 else 0
    }.sum()
    println("$line number of valid configs $numValid")
    return numValid
}

data class Arrangement(val template:String, val groups:List<Int>) {

    fun isValid(mask:String): Boolean {
        val config = applyTemplate(mask)
        //println("$mask -> $config")

        return validate(config)
    }

    private fun applyTemplate(mask:String) = let {
        var index = -1
        template.map { ch: Char ->
            when (ch) {
                '?' -> {
                    index++
                    if (mask[index] == '1') '#' else '.'
                }
                else -> ch
            }
        }
    }

    private fun validate(config:List<Char>):Boolean {
        //println("validating $groups, $config")
        var curRun = 0
        val list = mutableListOf<Int>()
        for (i in config.indices) {
            if (config[i] == '.') {
                if (curRun > 0) {
                    list.add(curRun)
                }
                curRun = 0
            } else if (config[i] == '#') {
                curRun++
            } else {
                error("Unexpected character: ${config[i]}")
            }
        }
        if (curRun > 0) {
            list.add(curRun)
        }
        if (groups == list) {
            //println("Match: $groups, $list")
        }

        return groups == list
    }

}