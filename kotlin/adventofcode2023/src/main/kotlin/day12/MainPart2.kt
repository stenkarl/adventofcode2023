package day12

val contents = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day12.txt").readLines()

val cache = mutableMapOf<Combo, Long>()

fun main() {
    //val first = contents.first().split(" ")
    var acc = 0L
    for (item in contents) {
        cache.clear()
        val split = item.split(" ")
        val groups = expandSecond(split[1]).split(",").map { it.toInt() }
        val count = countPermutations(expandFirst(split[0]), groups, 0, 0, "")

        println("Count $count")
        acc += count
    }
    println("Total $acc")
}

private fun expandFirst(str:String):String {
    val builder = StringBuilder("")
    for (i in 0..<5) {
        builder.append(str)
        if (i < 4) {
            builder.append("?")
        }
    }
    return builder.toString()
}

private fun expandSecond(str:String):String {
    val builder = StringBuilder("")
    for (i in 0..<5) {
        builder.append(str)
        if (i < 4) {
            builder.append(",")
        }
    }
    return builder.toString()
}

fun countPermutations(str:String, group:List<Int>, groupIndex:Int, curCount:Int, acc:String): Long {
    val combo = Combo(str, groupIndex, curCount)
    if (cache.containsKey(combo)) {
        //println("!! Found cache: $combo")
        return cache[combo]!!
    }
    //println("-> $str $group groupIndex $groupIndex curCount $curCount $acc")
    if (str.isEmpty()) {
        //println("<< Reached end of string >>")
        return if (curCount > 0 && groupIndex >= group.size) { // there are extra #s
            cache[combo] = 0
            0
        } else if (groupIndex < group.size - 1) { // there are more groups left
            cache[combo] = 0
            0
        } else if (groupIndex == group.size - 1 && curCount != group[groupIndex]) { // We're on the last group, but there's a count mismatch and we're out of characters
            cache[combo] = 0
            0
        } else {
            //println("A match! $acc")
            cache[combo] = 1
            1
        }
    }
    val ch = str[0]
    if (ch == '.') {
        val nextGroup = if (curCount > 0) {
            if (groupIndex >= group.size || curCount != group[groupIndex]) {
                //println("Not a match: curCount $curCount, group ${group[groupIndex]} groupIndex $groupIndex $acc")
                cache[combo] = 0
                return 0
            }
            groupIndex + 1
        } else { // not the first '.' so just advance the string
            groupIndex
        }
        val ret = countPermutations(str.substring(1), group, nextGroup, 0, acc + ch)
        cache[Combo(str.substring(1), nextGroup, 0)] = ret
        return ret
    } else if (ch == '#') {
        if (groupIndex >= group.size) {
            //println("Found # but no more groups left")
            cache[combo] = 0
            return 0
        } else if (curCount > group[groupIndex]) {
            //println("Too many #s $curCount, ${group[groupIndex]} $acc")
            cache[combo] = 0
            return 0
        }
        return countPermutations(str.substring(1), group, groupIndex, curCount + 1, acc + ch)
    } else if (ch == '?') {
        //println("Found '?'. Exploring both options curCount $curCount groupIndex $groupIndex $acc")
        val ret1 = countPermutations("." + str.substring(1), group, groupIndex, curCount, acc)
        val ret2 = countPermutations("#" + str.substring(1), group, groupIndex, curCount, acc)
        cache[Combo("." + str.substring(1), groupIndex, curCount)] = ret1
        cache[Combo("#" + str.substring(1), groupIndex, curCount)] = ret1
        val ret = ret1 + ret2
        cache[combo] = ret
        return ret
    } else {
        error("unexpected char $ch")
    }
}

data class Combo(val acc:String, val groupIndex:Int, val count:Int)