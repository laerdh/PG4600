package no.westerdals.PG4600.Innlevering1.model;


public class GameBoard {
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;

    private Cell[][] board = new Cell[ROWS][COLUMNS];
    private int cellsTaken = 0;



    public GameBoard() {
        init();
    }

    private void init() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public boolean checkWinVertical() {
        for (int i = 0; i < COLUMNS; i++) {
            Cell row1 = getCell(ROWS - 3, i);
            Cell row2 = getCell(ROWS - 2, i);
            Cell row3 = getCell(ROWS - 1, i);

            if (checkEqualCells(row1, row2, row3))
                return true;
        }
        return false;
    }

    public boolean checkWinHorizontal() {
        for (int i = 0; i < ROWS; i++) {
            Cell col1 = getCell(i, COLUMNS - 3);
            Cell col2 = getCell(i, COLUMNS - 2);
            Cell col3 = getCell(i, COLUMNS - 1);

            if (checkEqualCells(col1, col2, col3))
                return true;
        }
        return false;
    }

    public boolean checkWinDiagonal() {
        Cell topLeft = board[0][0];
        Cell bottomRight = board[2][2];

        Cell middle = board[1][1];

        Cell topRight = board[0][2];
        Cell bottomLeft = board[2][0];

        return checkEqualCells(topLeft, middle, bottomRight) || checkEqualCells(topRight, middle, bottomLeft);
    }

    public boolean checkWin() {
        return checkWinVertical() || checkWinHorizontal() || checkWinDiagonal();
    }

    public boolean markCell(Player player, int row, int column) {
        Cell cell = getCell(row, column);
            if (!cell.isTaken() && !isFull()) {
                cell.setSymbol(player.getSymbol());
                cellsTaken++;
                return true;
            }
        return false;
    }

    // Checks for equal cells. If first cell is taken, then compare cell 2 and cell 3
    private boolean checkEqualCells(Cell cell1, Cell cell2, Cell cell3) {
        return cell1.isTaken() && (cell1.equals(cell2) && cell2.equals(cell3));
    }

    private Cell getCell(int row, int column) {
        return board[row][column];
    }

    public boolean isFull() {
        return cellsTaken == (ROWS * COLUMNS);
    }

}
