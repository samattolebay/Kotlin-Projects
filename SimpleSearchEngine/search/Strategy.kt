package search

enum class Strategy {
    ALL, ANY, NONE;

    object Properties {
        fun printAll() {
            for ((count, item) in values().withIndex()) {
                if (count > 0) print(", ")
                print(item)
            }
            println()
        }
    }
}