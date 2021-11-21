package net.pdutta.mazes

fun main() {
    repeat(2) {
        val g = Grid(10, 10)
        Sidewinder.on(g)
        println(g)
    }
}
