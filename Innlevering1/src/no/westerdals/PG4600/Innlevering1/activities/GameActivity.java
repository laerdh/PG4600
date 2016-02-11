package no.westerdals.PG4600.Innlevering1.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import no.westerdals.PG4600.Innlevering1.R;
import no.westerdals.PG4600.Innlevering1.model.GameBoard;
import no.westerdals.PG4600.Innlevering1.model.Player;

/**
 * Created by larsdahl on 10.02.2016.
 */
public class GameActivity extends Activity {
    private static final int CELLS = 3;
    private static final int ROWS = 3;
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    private GameBoard gameBoard;
    private TextView[][] guiCells = new TextView[ROWS][CELLS];
    private TextView player1, player2;
    private Player playerX, playerO;
    private boolean turn = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard_layout);

        // Gather widgets (Setup gameboard, players etc)
        initGameBoard();
        initPlayerView();
        initPlayers();
        initCells();
    }

    private void initGameBoard() {
        gameBoard = new GameBoard();
    }

    private void initCells() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < CELLS; j++) {
                String cellID = "txtCell" + i + j;
                int textViewID = getResources().getIdentifier(cellID, "id", getPackageName());
                guiCells[i][j] = (TextView) findViewById(textViewID);
                guiCells[i][j].setTag(new TableData(i, j));

                guiCells[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableData tableData = (TableData) v.getTag();
                        updateCell(tableData.RowIndex, tableData.ColumnIndex);
                    }
                });

            }
        }
    }

    private void initPlayerView() {
        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);
    }

    private void initPlayers() {
        playerX = new Player(player1.getText().toString(), PLAYER_X);
        playerO = new Player(player2.getText().toString(), PLAYER_O);
    }

    private void updateCell(int row, int column) {
        if (turn) {
            gameBoard.markCell(playerX, row, column);
            if (gameBoard.checkWin()) {
                showToast("Player X won!");
                reloadGameActivity();
            }
            if (gameBoard.isFull()) {
                showToast("Draw!");
                reloadGameActivity();
            }
            guiCells[row][column].setText("X");
            turn = false;

        } else {
            gameBoard.markCell(playerO, row, column);
            if (gameBoard.checkWin()) {
                showToast("Player O won!");
                reloadGameActivity();
            }
            if (gameBoard.isFull()) {
                showToast("Draw!");
                reloadGameActivity();
            }
            guiCells[row][column].setText("O");
            turn = true;
        }
    }

    private void reloadGameActivity() {
        finish();
        startActivity(getIntent());
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Helper class to tag GUI cells
    public static class TableData {
        public final int RowIndex;
        public final int ColumnIndex;

        public TableData(int rowIndex, int columnIndex) {
            RowIndex = rowIndex;
            ColumnIndex = columnIndex;
        }
    }


}