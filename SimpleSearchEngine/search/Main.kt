package search

import java.io.File

fun main(args: Array<String>) {
    val words = mutableMapOf<String, MutableList<Int>>()
    val lines = mutableListOf<String>()
    getPeople(args[1], words, lines)
    menu(lines, words)
}

fun getPeople(fileName: String, words: MutableMap<String, MutableList<Int>>, lines: MutableList<String>) {
    lines.addAll(File(fileName).readLines())
    for (line in lines.indices) {
        val curLineWords = lines[line].split("\\s+".toRegex())
        for (curWord in curLineWords) {
            val lowerCurWord = curWord.toLowerCase()
            if (words[lowerCurWord] == null) {
                words[lowerCurWord] = mutableListOf(line)
            } else {
                words[lowerCurWord]?.add(line)
            }
        }
    }
}

fun menu(lines: MutableList<String>, words: MutableMap<String, MutableList<Int>>) {
    var menuOn = true
    while (menuOn) {
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")

        when (readLine()!!) {
            "1" -> search(lines, words)
            "2" -> listPeople(lines)
            "0" -> menuOn = false
            else -> println("\nIncorrect option! Try again.\n")
        }
    }
    println("Bye!")
}

fun listPeople(lines: MutableList<String>) {
    lines.forEach { println(it) }
    println()
}

fun search(lines: MutableList<String>, words: MutableMap<String, MutableList<Int>>) {
    println("\nEnter a name or email to search all suitable people.")
    val text = readLine()!!
    val foundLines = mutableSetOf<Int>()
    words.forEach { entry -> if (entry.key.equals(text, true)) foundLines.addAll(entry.value)}
    if (foundLines.isEmpty()) println("No matching people found.")
    else {
        println("${foundLines.size} persons found:")
        foundLines.forEach { println(lines[it]) }
    }
    println()
}