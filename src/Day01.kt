package day1

import utils.*

fun main() {
    fun part1(input: List<String>): Int {
        val r = Regex("\\d")
        return input.asSequence().map { l -> r.findAll(l).map { it.value }.toList() }
            .map { (it.first() + it.last()).toInt() }.sum()
    }

    fun part2(input: List<String>): Int {
        val numbers = buildMap {
            put("one", "1")
            put("two", "2")
            put("three", "3")
            put("four", "4")
            put("five", "5")
            put("six", "6")
            put("seven", "7")
            put("eight", "8")
            put("nine", "9")
        }
        val r = Regex("(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)|(\\d)")
        return input.sumOf { l ->
            buildList {
                var m = r.find(l)
                while (m != null) {
                    add(numbers[m.value] ?: m.value)
                    m = r.find(l, m.range.first + 1)
                }
            }.let { (it.first() + it.last()).toInt() }
        }
    }


    check(part1(readInput("Day01_test1") ?: return) == 142)
    check(part2(readInput("Day01_test2") ?: return) == 281)

    val input = readInput("Day01") ?: return
    part1(input).println()
    part2(input).println()
}
