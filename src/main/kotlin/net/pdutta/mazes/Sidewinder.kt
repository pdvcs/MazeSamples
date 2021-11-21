package net.pdutta.mazes

@Suppress("unused")
class Sidewinder {
    companion object {
        fun on(grid: Grid): Grid {
            for (row in grid.eachRow()) {
                val run = mutableListOf<Cell>()
                row.forEach { cell ->
                    run.add(cell)
                    val atEasternBoundary = (cell.east == null)
                    val atNorthernBoundary = (cell.north == null)
                    val shouldCloseOut = atEasternBoundary || (!atNorthernBoundary && ((0..1).random() == 0))
                    if (shouldCloseOut) {
                        val member = run.sample()
                        if (member.north != null) member.link(member.north!!)
                        run.clear()
                    } else {
                        cell.link(cell.east!!)
                    }
                }
            }
            return grid
        }

        private fun MutableList<Cell>.sample(): Cell {
            return this[this.indices.random()]
        }
    }
}