package day1

import utils.*

fun main() {
    fun part1(input: List<String>): Int {
        val r = Regex("\\d")
        return input.asSequence().map { l -> r.findAll(l).map { it.value }.toList() }
            .map { (it.first() + it.last()).toInt() }.sum()
    }

    fun part2(input: List<String>): Int {
        val r = Regex("(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)|(\\d)")
        return input.sumOf { l ->
            buildList {
                var m = r.find(l)
                while (m != null) {
                    add((m.groupValues.indexOfLast { it.isNotEmpty() }).takeIf { it < 10 }?.toString() ?: m.value)
                    m = r.find(l, m.range.first + 1)
                }
            }.let { (it.first() + it.last()).toInt() }
        }
    }


    checkEqual(part1(readInput("Day01_test1") ?: return), 142)
    checkEqual(part2(readInput("Day01_test2") ?: return), 281)

    val input = readInput("Day01") ?: return
    part1(input).println()
    part2(input).println()
}
