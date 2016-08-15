package no.westerdals.PG4600.Innlevering1.model;


public class Cell {
    private static final char EMPTY_CELL_TAG = ' ';
    private char symbol;

    public Cell(char symbol) {
        setSymbol(symbol);
    }

    public Cell() {
        this(EMPTY_CELL_TAG);
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }

    // Returns true if symbol is 'X' or 'O'
    public boolean isTaken() {
        return symbol != EMPTY_CELL_TAG;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Cell))
            return false;

        if (other == this)
            return true;

        Cell o = (Cell) other;
        return getSymbol() == o.getSymbol();
    }
}

