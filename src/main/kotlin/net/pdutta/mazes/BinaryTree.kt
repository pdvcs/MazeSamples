package net.pdutta.mazes

@Suppress("unused")
class BinaryTree {
    companion object {
        fun on(grid: Grid): Grid {
            for (cell in grid.eachCell()) {
                val neighbors = mutableListOf<Cell>()
                if (cell.north != null) neighbors.add(cell.north as Cell)
                if (cell.east != null) neighbors.add(cell.east as Cell)
                if (neighbors.size > 0) {
                    val neighbor = neighbors[(0 until neighbors.size).random()]
                    cell.link(neighbor)
                }
            }
            return grid
        }
    }
}
