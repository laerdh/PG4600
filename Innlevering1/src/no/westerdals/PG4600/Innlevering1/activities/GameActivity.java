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
import no.westerdals.PG4600.Innlevering1.model.Score;

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
    private TextView txtScoreX, txtScoreO;
    private Player playerX, playerO;
    private boolean turn = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard_layout);

        // Gather widgets (Setup gameboard, players etc)
        initGameBoard();
        initPlayers();
        initPlayerView();
        initScoreView();
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

        // Set player names
        player1.setText(playerX.getPlayerName());
        player2.setText(playerO.getPlayerName());
    }

    private void initScoreView() {
        txtScoreX = (TextView) findViewById(R.id.txtScoreX);
        txtScoreO = (TextView) findViewById(R.id.txtScoreO);

        txtScoreX.setText(String.valueOf(Score.scoreX));
        txtScoreO.setText(String.valueOf(Score.scoreO));
    }

    private void initPlayers() {
        // Get player names from GameActivity
        playerX = new Player(getIntent().getExtras().getString("player1"), PLAYER_X);
        playerO = new Player(getIntent().getExtras().getString("player2"), PLAYER_O);
    }

    private void updateCell(int row, int column) {
        if (turn) {
            if (gameBoard.markCell(playerX, row, column)) {
                if (gameBoard.checkWin()) {
                    showMessage(playerX.getPlayerName() + " won!");
                    updateScore();
                    reloadGameActivity();
                }
                if (gameBoard.isFull()) {
                    showMessage("Draw!");
                    reloadGameActivity();
                }
                guiCells[row][column].setText("X");
                turn = false;
            } else {
                showMessage("Already taken!");
            }

        } else {
            if (gameBoard.markCell(playerO, row, column)) {
                if (gameBoard.checkWin()) {
                    showMessage(playerO.getPlayerName() + " won!");
                    updateScore();
                    reloadGameActivity();
                }
                if (gameBoard.isFull()) {
                    showMessage("Draw!");
                    reloadGameActivity();
                }
                guiCells[row][column].setText("O");
                turn = true;
            } else {
                showMessage("Already taken!");
            }
        }
    }

    private void reloadGameActivity() {
        finish();
        startActivity(getIntent());
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateScore() {
        if (turn) {
            Score.scoreX++;
            txtScoreX.setText(String.valueOf(Score.scoreX));
        } else {
            Score.scoreO++;
            txtScoreO.setText(String.valueOf(Score.scoreO));
        }
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