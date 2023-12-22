package day5

//val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day5sample.txt").readLines()
val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day5.txt").readLines()

fun main() {
    val (seeds, transformers) = init()
    //part1(seeds, transformers)
    part2(seeds, transformers)
}

fun init():Pair<List<Long>, List<Transformer>> {
    println(lines)
    val transformers = mutableListOf<Transformer>()

    var index = 2
    while (index < lines.size) {
        index = createTransformer(transformers, index)
    }

    println(transformers)
    val seeds = lines[0].split(": ")[1].split(" ").map {it.toLong()}
    println(seeds)

    return Pair(seeds, transformers)
}

fun part1(seeds:List<Long>, transformers:List<Transformer>) {
    println("Day 5, part 1")

    println("Smallest: ${mapSeeds(seeds, transformers)}")
}

fun part2(seeds:List<Long>, transformers:List<Transformer>) {
    var curMin = Long.MAX_VALUE
    var count = 0L
    var total = 0L
    for (i in seeds.indices step 2) {
        total += seeds[i + 1]
    }
    println("Total $total")
    for (i in seeds.indices step 2) {
        for (j in seeds[i]..<seeds[i] + seeds[i + 1] - 1) {
            val curValue = mapSeed(j, transformers)
            if (curValue < curMin) {
                curMin = curValue
                println("new min: $curMin")
            }
            //count++
            //if (count.mod(10000000L) == 0L) {
                //println("Count $count, value $j")
            //}
        }
    }

    println("Smallest: $curMin")
}

fun createTransformer(transformers:MutableList<Transformer>, index:Int):Int {
    val name = lines[index]
    println("name: $name")
    var lineIndex = index + 1
    var line = lines[lineIndex]
    val ranges = mutableListOf<Range>()
    while (line != "") {
        val split = line.split(" ")
        ranges.add(Range(split[1].toLong(), split[0].toLong(), split[2].toLong()))
        lineIndex++
        line = lines[lineIndex]
    }
    transformers.add(Transformer(name, ranges))
    return lineIndex + 1
}

fun mapSeeds(seeds:List<Long>, transformers:List<Transformer>):Long =
    seeds.map {
        mapSeed(it, transformers)
    }.min()

fun mapSeed(seed:Long, transformers:List<Transformer>): Long {
    var mappedValue = seed
    for (t in transformers) {
        mappedValue = t.map(mappedValue)
    }
    return mappedValue
}


data class Transformer(val name:String, val ranges:List<Range>) {

    fun map(value:Long): Long {
        for (r in ranges) {
            val mappedValue = r.map(value)
            if (mappedValue > -1L) {
                return mappedValue
            }
        }
        return value
    }
}

data class Range(val source:Long, val dest:Long, val length:Long) {

    fun map(value:Long): Long =
        if (value >= source && value <= source + length) {
            dest + (value - source)
        } else {
            -1L
        }
}