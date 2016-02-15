package no.westerdals.PG4600.Innlevering1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import no.westerdals.PG4600.Innlevering1.R;
import no.westerdals.PG4600.Innlevering1.model.GameBoard;
import no.westerdals.PG4600.Innlevering1.model.Player;
import no.westerdals.PG4600.Innlevering1.model.Scoreboard;


public class GameActivity extends Activity {
    private static final int RESTART_GAME_DELAY = 2000;

    private GameBoard gameBoard;
    private TextView[][] guiCells;
    private TextView txtPlayerX, txtPlayerO;
    private TextView txtStatus;
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
        initUICells();

        // Updates GUI with who's turn
        updateStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemNewgame:
                this.finish();
                return true;
            case R.id.itemScoreboard:
                loadScoreboard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadScoreboard() {
        Intent scoreboard = new Intent(getApplicationContext(), ResultActivity.class);
        startActivity(scoreboard);
    }

    private void initGameBoard() {
        gameBoard = new GameBoard();
    }

    private void initUICells() {
        int rows = gameBoard.getRows();
        int columns = gameBoard.getColumns();

        guiCells = new TextView[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String cellID = "txtCell" + i + j;
                int textViewID = getResources().getIdentifier(cellID, "id", getPackageName());
                guiCells[i][j] = (TextView) findViewById(textViewID);
                guiCells[i][j].setTag(new TableData(i, j));

                guiCells[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableData tableData = (TableData) v.getTag();
                        checkTurn(tableData);
                    }
                });

            }
        }
    }

    private void initPlayerView() {
        txtPlayerX = (TextView) findViewById(R.id.player1);
        txtPlayerO = (TextView) findViewById(R.id.player2);
        txtStatus = (TextView) findViewById(R.id.txtStatus);

        // Set player names
        txtPlayerX.setText(playerX.getPlayerName());
        txtPlayerO.setText(playerO.getPlayerName());
    }

    private void initPlayers() {
        String symbolX = getResources().getString(R.string.playerX);
        String symbolO = getResources().getString(R.string.playerO);

        // Get player names from GameActivity
        playerX = new Player(getIntent().getExtras().getString("player1"), symbolX.charAt(0));
        playerO = new Player(getIntent().getExtras().getString("player2"), symbolO.charAt(0));
    }

    private void checkTurn(TableData tableData) {
        if (turn) {
            setCell(playerX, tableData.RowIndex, tableData.ColumnIndex);
        } else {
            setCell(playerO, tableData.RowIndex, tableData.ColumnIndex);
        }
    }

    private void setCell(Player player, int row, int column) {
        if (gameBoard.markCell(player, row, column)) {
            guiCells[row][column].setText(String.valueOf(player.getSymbol()));

            if (gameBoard.checkWin()) {
                logResult(turn ? Result.PLAYER_X_WIN : Result.PLAYER_O_WIN);
                restartGame();
            } else if (gameBoard.isFull()) {
                logResult(Result.TIE);
                restartGame();
            }
            turn = !turn;
            updateStatus();
        } else {
            showMessage("Already taken!");
        }
    }

    private void restartGame() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Could have implemented reset method
                gameBoard = new GameBoard();

                // Empty cells
                for (int i = 0; i < gameBoard.getRows(); i++) {
                    for (int j = 0; j < gameBoard.getColumns(); j++) {
                        guiCells[i][j].setText("");
                    }
                }

                updateStatus();
            }
        }, RESTART_GAME_DELAY);
    }

    private void logResult(Result status) {
        switch (status) {
            case PLAYER_X_WIN:
                Scoreboard.addResult(playerX.getPlayerName() + " won against " + playerO.getPlayerName());
                showMessage(playerX.getPlayerName() + " won!");
                break;
            case PLAYER_O_WIN:
                Scoreboard.addResult(playerO.getPlayerName() + " won against " + playerX.getPlayerName());
                showMessage(playerO.getPlayerName() + " won!");
                break;
            case TIE:
                Scoreboard.addResult(playerX.getPlayerName() + " vs " + playerO.getPlayerName() + " ended in a tie");
                showMessage("Draw!");
                break;
        }
    }

    enum Result {
        PLAYER_X_WIN, PLAYER_O_WIN, TIE
    }

    private void updateStatus() {
        String player = turn ? playerX.getPlayerName() : playerO.getPlayerName();
        txtStatus.setText(player + "'s turn");
    }

    private void showMessage(String message) {
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