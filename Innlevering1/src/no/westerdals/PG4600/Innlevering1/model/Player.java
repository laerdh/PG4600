package no.westerdals.PG4600.Innlevering1.model;

/**
 * Created by larsdahl on 09.02.2016.
 */
public class Player {
    private String playerName;
    private char symbol;

    public Player(String playerName, char symbol) {
        setPlayerName(playerName);
        setSymbol(symbol);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
