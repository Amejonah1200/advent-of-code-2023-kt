package utils

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("inputs/$name.txt").takeIf { it.exists() }?.readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> T.alsoPrintln() = also(::println)

fun <T> T.alsoPrintln(transform: (T) -> Any) = also { transform(this).println() }

fun checkEqual(result: Int, expected: Int) = check(result == expected) { "Expected $expected, got $result" }
