package search

import java.io.File

class Program(fileName: String) {
    private var running: Boolean = true
    private val words = mutableMapOf<String, MutableList<Int>>()
    private val lines = mutableListOf<String>()

    init {
        getPeople(fileName, words, lines)
    }

    private fun getPeople(fileName: String, words: MutableMap<String, MutableList<Int>>, lines: MutableList<String>) {
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
    fun menu() {
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")

        when (readLine()!!) {
            "1" -> search(lines, words)
            "2" -> listPeople(lines)
            "0" -> exitProgram()
            else -> println("\nIncorrect option! Try again.\n")
        }
    }

    private fun exitProgram() {
        running = false
        println("Bye!")
    }

    fun isRunning(): Boolean {
        return running
    }

    private fun listPeople(lines: MutableList<String>) {
        println()
        lines.forEach { println(it) }
        println()
    }

    private fun search(lines: MutableList<String>, words: MutableMap<String, MutableList<Int>>) {
        print("\nSelect a matching strategy: ")
        Strategy.Properties.printAll()
        val strategy = Strategy.valueOf(readLine()!!)
        println("\nEnter a name or email to search all suitable people.")
        val queries = readLine()!!.split("\\s+".toRegex())
        val foundLines = mutableSetOf<Int>()
        when (strategy) {
            Strategy.ALL -> {
                val mainSet = words[queries[0].toLowerCase()]
                if (mainSet != null) {
                    for (index in mainSet){
                        var allContain = true
                        for (query in queries) {
                            val tempSet = words[query.toLowerCase()]
                            if (tempSet == null || !tempSet.contains(index)) {
                                allContain = false
                                break
                            }
                        }
                        if (allContain) {
                            foundLines.add(index)
                        }
                    }
                }
            }
            Strategy.ANY -> {
                for (query in queries) {
                    words.forEach { entry -> if (entry.key.equals(query, true)) foundLines.addAll(entry.value)}
                }
            }
            Strategy.NONE -> {
                for (index in lines.indices) {
                    foundLines.add(index)
                }
                for (query in queries) {
                    val tempSet = words[query.toLowerCase()]
                    if (tempSet != null) {
                        foundLines.removeAll(tempSet)
                    }
                }
            }
        }
        if (foundLines.isEmpty()) println("\nNo matching people found.")
        else {
            println("\n${foundLines.size} person${if (foundLines.size > 1) "s" else ""} found:")
            foundLines.forEach { println(lines[it]) }
        }
        println()
    }
}
