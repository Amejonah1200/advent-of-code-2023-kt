package day3

import utils.*

fun main() {

    val rn = Regex("\\d+")
    val rs = Regex(" +")

    fun part0(input: List<String>, aSize: Int, bSize: Int) = input.map {
        val split = rs.split(it)
        val a = split.subList(2, aSize + 2)
        val b = split.takeLast(bSize)
        a.filter(b::contains).size
    }

    fun part1(input: List<Int>): Int {
        return input.sumOf { s ->
            if (s == 0) 0
            else 1 shl (s - 1)
        }
    }


    fun part2(input: List<Int>): Int {
        val stack = input.withIndex().associate { it.index to 1 }.toMutableMap()
        repeat(input.size) { index ->
            repeat(input[index]) { other ->
                val otherIndex = index + other + 1
                stack[otherIndex] = stack.getOrDefault(otherIndex, 0) + stack.getOrDefault(index, 0)
            }
        }
        return stack.values.sum()
    }


    val testSchema = part0(readInput("Day04_test") ?: return, 5, 8)
    checkEqualQuick(part1(testSchema), 13)
    checkEqualQuick(part2(testSchema), 30)

    val input = part0(readInput("Day04") ?: return, 10, 25)
    part1(input).println()
    part2(input).println()
}
