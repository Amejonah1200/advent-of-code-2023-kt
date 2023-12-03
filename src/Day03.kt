package day3

import utils.*


fun IntRange.expand(leftE: Int = 0, rightE: Int = 0) = IntRange(start - leftE, endInclusive + rightE)
val IntRange.length: Int get() = last - first
fun IntRange.count() = length / step + if (length > 0 && length % step == 0) 1 else 0
fun main() {

    val rn = Regex("\\d+")

    fun findAllPartNumbers(
        l: String,
        i: Int,
        input: List<String>,
        predicate: (Char) -> Boolean
    ) = rn.findAll(l).filter { m ->
        val searchRange = IntRange(
            (m.range.first - 1).coerceAtLeast(0),
            (m.range.last + 1).coerceAtMost(l.lastIndex)
        )
        sequence {
            yield(l[searchRange.first])
            yield(l[searchRange.last])
            if (i > 0) yieldAll(input[i - 1].asSequence().drop(searchRange.first).take(searchRange.count()))
            if (i < l.lastIndex) yieldAll(
                input[i + 1].asSequence().drop(searchRange.first).take(searchRange.count())
            )
        }.any(predicate)
    }.toList()

    fun part1(input: List<String>): Int {
        val predicate: (Char) -> Boolean = { c -> c != '.' && !c.isDigit() }
        return input.asSequence().flatMapIndexed { i, l ->
            findAllPartNumbers(l, i, input, predicate)
        }.sumOf { it.value.toInt() }
    }


    fun part2(input: List<String>): Int {
        val predicate: (Char) -> Boolean = { c -> c == '*' }
        val allPartNumbers = input.asSequence().mapIndexed { i, l ->
            findAllPartNumbers(l, i, input, predicate)
        }.toList()
        return input.asSequence().flatMapIndexed { i, s ->
            println("$i $s")
            s.withIndex().filter { it.value == '*' }.map {(index, c) ->
                println("$index")
                sequence {
                    if (i > 0) yieldAll(allPartNumbers[i - 1].filter { index in it.range.expand(1, 1) })
                    if (i < s.lastIndex) yieldAll(allPartNumbers[i + 1].filter { index in it.range.expand(1, 1) })
                    yieldAll(allPartNumbers[i].filter { it.range.first - 1 == index || index == it.range.last + 1 })
                }.map { it.value.toInt() }.toList().let { if(it.size == 2) it.reduce { acc, i -> acc * i } else 0 }
            }
        }.sum()
    }


    val testSchema = readInput("Day03_test") ?: return
    checkEqual(part1(testSchema), 4361)
    checkEqual(part2(testSchema), 467835)
    checkEqual(part2(readInput("Day03_test_own2") ?: return), 467835)

    val input = readInput("Day03") ?: return
    part1(input).println()
    part2(input).println()
}
