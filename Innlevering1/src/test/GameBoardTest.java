package test;

import no.westerdals.PG4600.Innlevering1.model.GameBoard;
import no.westerdals.PG4600.Innlevering1.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by larsdahl on 10.02.2016.
 */
public class GameBoardTest {
    private static int COLS = 3;
    private static int ROWS = 3;
    private GameBoard board;

    @Before
    public void setUp() throws Exception {
        board = new GameBoard();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCheckWinVertical() throws Exception {
        Player player1 = new Player("Tester", 'X');

        board.placeMark(player1, 0, 0);
        board.placeMark(player1, 1, 0);
        board.placeMark(player1, 2, 0);

        assertTrue(board.checkWinVertical());
    }

    @Test
    public void testDoesNotWinVerticalStoppedByPlayer2() throws Exception {
        Player player1 = new Player("Tester1", 'X');
        Player player2 = new Player("Tester2", 'O');
        board.placeMark(player1, 0, 0);
        board.placeMark(player2, 1, 0);
        board.placeMark(player1, 2, 0);

        assertFalse(board.checkWinVertical());
    }

    @Test
    public void testDoesNotWinVerticalDueToFreeCells() throws Exception {
        Player player1 = new Player("Tester1", 'X');
        board.placeMark(player1, 0, 0);
        board.placeMark(player1, 1, 0);

        assertFalse(board.checkWinVertical());
    }

    @Test
    public void testCheckWinHorizontal() throws Exception {
        Player player1 = new Player("Tester", 'X');
        board.placeMark(player1, 0, 0);
        board.placeMark(player1, 0, 1);
        board.placeMark(player1, 0, 2);

        assertTrue(board.checkWinHorizontal());
    }

    @Test
    public void testCheckWinLeftDiagonal() throws Exception {
        Player player1 = new Player("Tester", 'O');

        for (int i = 0; i < ROWS; i++) {
            board.placeMark(player1, i, i);
        }

        assertTrue(board.checkWinDiagonal());
    }

    @Test
    public void testCheckWinLeftDiagonalReversed() throws Exception {
        Player player1 = new Player("Tester", 'O');

        board.placeMark(player1, 2, 2);
        board.placeMark(player1, 1, 1);
        board.placeMark(player1, 0, 0);

        assertTrue(board.checkWinDiagonal());
    }

    @Test
    public void testCheckWinRightDiagonal() throws Exception {
        Player player1 = new Player("Tester", 'O');

        board.placeMark(player1, 0, 2);
        board.placeMark(player1, 1, 1);
        board.placeMark(player1, 2, 0);

        assertTrue(board.checkWinDiagonal());
    }

    @Test
    public void testCheckWinRightDiagonalReversed() throws Exception {
        Player player1 = new Player("Tester", 'O');

        board.placeMark(player1, 2, 0);
        board.placeMark(player1, 1, 1);
        board.placeMark(player1, 0, 2);

        assertTrue(board.checkWinDiagonal());
    }

    @Test
    public void testMarkCell() throws Exception {
        Player playerTest1 = new Player("Tester1", 'X');

        assertTrue(board.placeMark(playerTest1, 0, 0));
    }

    @Test
    public void testMarkCellWhenCellIsTakenReturnsFalse() throws Exception {
        Player playerTest1 = new Player("Tester1", 'X');
        Player playerTest2 = new Player("Tester2", 'X');
        board.placeMark(playerTest1, 0, 0);

        assertFalse(board.placeMark(playerTest2, 0, 0));
    }

    @Test
    public void testCheckBoardNotFull() throws Exception {
        assertFalse(board.isFull());
    }

    @Test
    public void testFillBoard() throws Exception {
        Player player1 = new Player("Tester", 'X');

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                assertTrue("Cant place marker at row " + i + ", column: " + j, board.placeMark(player1, i, j));
            }
        }
        assertTrue(board.isFull());
    }

}