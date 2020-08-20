package games.gameOfFifteen

import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game
{
    private val board = createGameBoard<Int?>(4)

    override fun initialize()
    {
        val permission: MutableList<Int?> = initializer.initialPermutation.toMutableList()
        permission.add(null)
        board.getAllCells().forEach { board[it] = permission[(it.i - 1) * 4 + it.j - 1] }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean =
            board.getAllCells().all { it.i + it.j == 8 || (it.i - 1) * 4 + it.j == board[it] }

    override fun processMove(direction: Direction)
    {
        with(board) {
            val oldCell = find { it == null }
            val newCell = oldCell?.getNeighbour(direction.reversed())
            newCell?.let {
                this[oldCell] = this[newCell]
                this[newCell] = null
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}

fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

