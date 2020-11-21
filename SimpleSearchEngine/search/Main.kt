package search

fun main(args: Array<String>) {
    val program = Program(args[1])
    while (program.isRunning()) {
        program.menu()
    }
}