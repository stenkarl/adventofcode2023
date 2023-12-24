package day7

val lines = java.io.File("/Users/sten/dev/adventofcode2023/kotlin/adventofcode2023/src/main/resources/day7.txt").readLines()

fun main() {
    println("Day 7")
    println(lines)
    part2(buildRounds())

}

fun buildRounds(): List<Round> =
    lines.map {
        val split = it.split(" ")
        Round(
            Hand(
                split[0].map {
                    Card(it)
                }
            ),
            split[1].toInt()
        )
    }

fun part1(rounds:List<Round>) {
    println(rounds)

    //println(listOf(Card('Q'), Card('3'), Card('6'), Card('A'), Card('T'), Card('2')).sorted())

    //val h1 = Hand(listOf(Card('Q'), Card('9'), Card('8'), Card('9'), Card('8')))
    val ranked = rounds.sorted()
    val total = ranked.foldIndexed(0) {index: Int, acc: Int, r: Round ->
        acc + (r.bid * (index + 1))
    }
    println(total)
}

fun part2(rounds: List<Round>) {
    val ranked = rounds.sorted()
    val total = ranked.foldIndexed(0) {index: Int, acc: Int, r: Round ->
        acc + (r.bid * (index + 1))
    }
    println(total)
}

data class Card(val value: Char) : Comparable<Card> {

    override fun compareTo(other:Card): Int =
        part2Ordering.indexOf(value).compareTo(part2Ordering.indexOf(other.value))

    companion object {
        private val part2Ordering = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()
        private val part1Ordering = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()

    }
}

data class Hand(val cards: List<Card>) : Comparable<Hand> {

    val type: Type = findType(cards)
    val alteredType = findType(findAlteredCards())

    override fun compareTo(other:Hand): Int {
        val typeCompare = alteredType.ordinal.compareTo(other.alteredType.ordinal)
        return if (typeCompare == 0) {
            for (i in cards.indices) {
                val cardEquality = cards[i].compareTo(other.cards[i])
                if (cardEquality != 0) {
                    return cardEquality
                }
            }
            return 0
        } else {
            typeCompare
        }
    }

    private fun findType(c: List<Card>): Type {
        val setSize = c.toSet().size
        if (setSize == 1) {
            return Type.FIVE_OF_A_KIND
        } else if (setSize == 2) {
            val count = c.count { it == c.first()}
            return if (count == 3 || count == 2) {
                Type.FULL_HOUSE
            } else {
                Type.FOUR_OF_A_KIND
            }
        } else if (setSize == 3) {
            var maxCount = 1
            for (card in c) {
                val count = c.count { it == card }
                if (count > maxCount) {
                    maxCount = count
                }
            }
            return if (maxCount == 3) {
                Type.THREE_OF_A_KIND
            } else {
                Type.TWO_PAIR
            }
        } else if (setSize == 4) {
            return Type.ONE_PAIR
        }
        return Type.HIGH_CARD
    }

    fun findAlteredCards(): List<Card> {
        if (!cards.contains(Card('J')) || cards.count { it == Card('J')} == 5) {
            return cards
        }
        val noJokers = cards.filter { it != Card('J') }
        val max = noJokers.groupBy { it }
            .map { Pair(it.key, it.value.size) }
            .sortedWith(compareBy({it.second}, {it.first}))
            .reversed().first().first

        return cards.map {
            if (it.value == 'J') {
                max
            } else {
                it
            }
        }
    }

}

data class Round(val hand: Hand, val bid: Int) : Comparable<Round> {

    override fun compareTo(other: Round): Int =
        hand.compareTo(other.hand)
}

enum class Type {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}