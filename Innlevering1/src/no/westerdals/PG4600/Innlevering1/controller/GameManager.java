package no.westerdals.PG4600.Innlevering1.controller;

import no.westerdals.PG4600.Innlevering1.model.GameBoard;
import no.westerdals.PG4600.Innlevering1.model.Player;

/**
 * Created by larsdahl on 09.02.2016.
 */
public class GameManager {
    private static final char PLAYER_X_SYMBOL = 'X';
    private static final char PLAYER_O_SYMBOL = 'O';
    private boolean gameOn = false;
    private static GameBoard gameBoard = new GameBoard();
    private Player player1, player2;



    public GameManager(String player1, String player2) {
        setPlayerX(player1);
        setPlayerO(player2);
        initGameboard();
    }

    private void initGameboard() {
        gameBoard = new GameBoard();
    }

    public void setPlayerX(String playerName) {
        player2 = new Player(playerName, PLAYER_X_SYMBOL);
    }

    public void setPlayerO(String playerName) {
        player1 = new Player(playerName, PLAYER_O_SYMBOL);
    }

    private void start() {
        gameOn = true;

        while (gameOn) {

        }
    }

}
