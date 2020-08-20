package board

import board.Direction.*
import java.lang.IllegalArgumentException
import kotlin.math.*

open class mySquareBoard(override val width: Int) : SquareBoard
{
    protected val board: List<List<Cell>> =
            List(width) { i -> List(width) { j -> Cell(i + 1, j + 1) } }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
            if (i in 1..width && j in 1..width)
                board[i - 1][j - 1]
            else null

    override fun getCell(i: Int, j: Int): Cell =
            getCellOrNull(i, j) ?: throw IllegalArgumentException("Wrong Index")

    override fun getAllCells(): Collection<Cell> = board.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell>
    {
        var ans = mutableListOf<Cell>()
        val start = max(min(jRange.first, width), 1)
        val stop = max(min(jRange.last, width), 1)
        if (stop < start)
            for (j in start downTo stop)
                ans.add(getCell(i, j))
        else for (j in start..stop)
            ans.add(getCell(i, j))
        return ans
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell>
    {
        var ans = mutableListOf<Cell>()
        val start = max(min(iRange.first, width), 1)
        val stop = max(min(iRange.last, width), 1)
        if (stop < start)
            for (i in start downTo stop)
                ans.add(getCell(i, j))
        else for (i in start..stop)
            ans.add(getCell(i, j))
        return ans
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
            when (direction)
            {
                UP    -> getCellOrNull(this.i - 1, this.j)
                DOWN  -> getCellOrNull(this.i + 1, this.j)
                LEFT  -> getCellOrNull(this.i, this.j - 1)
                RIGHT -> getCellOrNull(this.i, this.j + 1)
            }

}

class myGameBoard<T>(width: Int) : mySquareBoard(width), GameBoard<T>
{
    private var mapBoard: MutableMap<Cell, T?> =
            board.flatten().map { it to null }.toMap().toMutableMap()

    override fun get(cell: Cell): T? = mapBoard[cell]

    override fun set(cell: Cell, value: T?) = run { mapBoard[cell] = value }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
            mapBoard.filter { predicate(it.value) }.keys

    override fun find(predicate: (T?) -> Boolean): Cell? =
            mapBoard.filterValues { predicate(it) }.keys.firstOrNull()

    override fun any(predicate: (T?) -> Boolean): Boolean = mapBoard.any { predicate(it.value) }

    override fun all(predicate: (T?) -> Boolean): Boolean = mapBoard.all { predicate(it.value) }

}

fun createSquareBoard(width: Int): SquareBoard = mySquareBoard(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = myGameBoard(width)

