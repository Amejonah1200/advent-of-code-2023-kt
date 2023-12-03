package day2

import utils.*

val rs = Regex("(?:\\d+ .+?,?)+(;|\\Z)")
val rc = Regex("(\\d+) ([rgb])")

data class GameSet(val r: Int, val g: Int, val b: Int)

fun parseGame(l: String) = rs.findAll(l).map {
    rc.findAll(it.value).associate { it.groupValues[2] to it.groupValues[1].toInt() }
}.map { set -> GameSet(set["r"] ?: 0, set["g"] ?: 0, set["b"] ?: 0) }.toList()

fun main() {

    fun part1(input: List<List<GameSet>>, r: Int, g: Int, b: Int): Int =
        input.asSequence()
            .mapIndexedNotNull { index, s -> if (s.all { set -> set.r <= r && set.g <= g && set.b <= b }) index + 1 else null }
            .sum()

    fun part1(input: List<List<GameSet>>): Int = part1(input, 12, 13, 14)

    fun part2(input: List<List<GameSet>>): Int {
        fun List<GameSet>.minGame() = GameSet(this.maxOf { it.r }, this.maxOf { it.g }, this.maxOf { it.b })
        fun GameSet.power() = maxOf(r, 1) * maxOf(g, 1) * maxOf(b, 1)
        return input.sumOf { it.minGame().power() }
    }


    checkEqual(part1(readGames("Day02_test1") ?: return), 8)
    checkEqual(part2(readGames("Day02_test2") ?: return), 2286)

    val input = readGames("Day02") ?: return
    part1(input).println()
    part2(input).println()
}

private fun readGames(s: String) = readInput(s)?.map(::parseGame)
