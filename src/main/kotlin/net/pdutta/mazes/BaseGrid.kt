package net.pdutta.mazes

class Cell(val row: Int, val column: Int) {
    var north: Cell? = null
    var east: Cell? = null
    var south: Cell? = null
    var west: Cell? = null
    private var mLinks = mutableMapOf<Cell, Boolean>()

    @Suppress("unused")
    fun link(cell: Cell, bidi: Boolean = true): Cell {
        mLinks[cell] = true
        if (bidi) cell.link(this, false)
        return this
    }

    @Suppress("unused")
    fun unlink(cell: Cell, bidi: Boolean = true): Cell {
        mLinks.remove(cell)
        if (bidi) cell.unlink(this, false)
        return this
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun links(): Set<Cell> {
        return mLinks.keys.toSet()
    }

    fun isLinked(cell: Cell?): Boolean {
        return mLinks.containsKey(cell)
    }

    @Suppress("unused")
    fun neighbors(): List<Cell> {
        val neighborsList = mutableListOf<Cell>()
        if (north != null) {
            neighborsList.add(north as Cell)
        }
        if (east != null) {
            neighborsList.add(east as Cell)
        }
        if (south != null) {
            neighborsList.add(south as Cell)
        }
        if (west != null) {
            neighborsList.add(west as Cell)
        }
        return neighborsList.toList()
    }

    override fun toString(): String {
        val sb = StringBuffer()
        for (cell in links()) {
            sb.append("{").append(cell.row).append(",").append(cell.column).append("} ")
        }
        return "Cell row=$row, column=$column, links=$sb"
    }

    companion object {
        @Suppress("unused")
        val INVALID = Cell(-1, -1)
    }
}

@Suppress("MemberVisibilityCanBePrivate")
open class Grid(val rows: Int, val columns: Int) {
    var grid = Array(rows) { r -> Array(columns) { c -> Cell(r, c) } }

    init {
        grid.forEach { r ->
            r.forEach { c ->
                val row = c.row
                val col = c.column
                c.north = this[row - 1, col]
                c.east = this[row, col + 1]
                c.south = this[row + 1, col]
                c.west = this[row, col - 1]
            }
        }
    }

    open operator fun get(r: Int, c: Int): Cell? {
        return if (r in 0 until rows && c in 0 until columns) {
            grid[r][c]
        } else {
            null
        }
    }

    fun eachRow(): Sequence<Array<Cell>> = sequence {
        grid.forEach { r -> yield(r) }
    }

    fun eachCell(): Sequence<Cell> = sequence {
        grid.forEach { r ->
            r.forEach { c -> yield(c) }
        }
    }

    @Suppress("unused")
    fun randomCell(): Cell {
        return grid[(0 until rows).random()][(0 until columns).random()]
    }

    @Suppress("unused")
    fun size(): Int {
        return rows * columns
    }

    operator fun set(r: Int, c: Int, newValue: Cell) {
        grid[r][c] = newValue
    }

    override fun toString(): String {
        val output = StringBuffer()
        output.append("+").append("---+".repeat(columns)).append("\n")
        for (r in eachRow()) {
            val top = StringBuffer("|")
            val bottom = StringBuffer("+")
            r.forEach { c ->
                val body = "   "
                val eastBoundary = if (c.isLinked(c.east)) " " else "|"
                top.append(body).append(eastBoundary)

                val southBoundary = if (c.isLinked(c.south)) "   " else "---"
                val corner = "+"
                bottom.append(southBoundary).append(corner)
            }
            output.append(top).append("\n").append(bottom).append("\n")
        }
        return output.toString()
    }
}
