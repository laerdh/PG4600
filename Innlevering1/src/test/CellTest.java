package test;

import no.westerdals.PG4600.Innlevering1.model.Cell;
import org.junit.Test;

import static org.junit.Assert.*;


public class CellTest {

    @Test
    public void testSetSymbol() throws Exception {
        char expected = 'X';
        Cell cell = new Cell();
        cell.setSymbol(expected);

        assertEquals(expected, cell.getSymbol());
    }

    @Test
    public void testGetSymbol() throws Exception {
        char expected = 'X';
        Cell cell = new Cell('X');

        assertEquals(expected, cell.getSymbol());
    }

    @Test
    public void testIsTaken() throws Exception {
        Cell cell = new Cell('X');

        assertTrue(cell.isTaken());
    }

    @Test
    public void testIsNotTaken() throws Exception {
        Cell cell = new Cell();

        assertFalse(cell.isTaken());
    }

    @Test
    public void testCellIsEqual() throws Exception {
        Cell cell1 = new Cell('X');
        Cell cell2 = new Cell('X');

        assertTrue(cell1.equals(cell2));
    }

    @Test
    public void testCellIsNotEqual() throws Exception {
        Cell cell1 = new Cell('X');
        Cell cell2 = new Cell('O');

        assertFalse(cell1.equals(cell2));
    }
}