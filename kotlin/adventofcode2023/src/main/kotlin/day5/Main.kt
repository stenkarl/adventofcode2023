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
    var total = 0L
    for (i in seeds.indices step 2) {
        total += seeds[i + 1]
    }
    println("Total $total")
    for (i in seeds.indices step 2) {
        val start = seeds[i]
        val len = seeds[i + 1]
        val end = start + len - 1
        println("start $start, end $end, len $len")
        for (j in start..end) {
            //println("Processing seed $j")
            val curValue = mapSeed(j, transformers)
            //println("Seed $j maps to $curValue")
            if (curValue < curMin) {
                curMin = curValue
                println("new min: $curMin")
            }
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
    val locList = mutableSetOf<Long>()
    exploreMaps(seed, 0, transformers, seed, locList)
    //println("Found: $locList for seed $seed")
    //println("Min loc ${locList.min()}")
    //for (t in transformers) {
        //mappedValue = t.map(mappedValue)
    //}
    return locList.min()
}

fun exploreMaps(topLevelSeed:Long, index:Int, transformers:List<Transformer>, value:Long, locList:MutableSet<Long>) {
    //println("exploreMaps $topLevelSeed, value:$value, index $index ${transformers[index].name}")
    //for (i in index..<transformers.size) {
        //println("Processing ${transformers[index].name}, value:$value")
        val list = transformers[index].map(value)
        if (index == transformers.size - 1) {
            locList.addAll(list)
            //println("At the end for $topLevelSeed currentValue: $value: ${transformers[index].name} $list, locList $locList")
            return
        }
        for (v in list) {
            //println("Going to exploreMaps $topLevelSeed, v:$v, index $index ${transformers[index].name}")
            exploreMaps(topLevelSeed, index + 1, transformers, v, locList)
            //println("Finished exploreMaps $topLevelSeed, v:$v, index $index ${transformers[index].name}")
        }
    //}
}

data class Transformer(val name:String, val ranges:List<Range>) {

    fun map(value:Long): List<Long> {
        val list = mutableListOf<Long>()
        for (r in ranges) {
            val mappedValue = r.map(value)
            if (mappedValue > -1L) {
                //return mappedValue
                list.add(mappedValue)
            }
        }
        if (list.isEmpty()) {
            list.add(value)
        }
        if (list.size > 1) {
            println("Multiple mappings found for $name: $list")
        }
        return list
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