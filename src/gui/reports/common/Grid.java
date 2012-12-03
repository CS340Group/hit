package gui.reports.common;

import java.util.ArrayList;

/**
 * The Grid is a table of strings. It can be constructed by passing in a list of
 * header strings. This will establish the number of columns in a table.
 * New rows can be added by passing a list of strings to newRow().
 */
public class Grid {

    private ArrayList<String> headers;
    private ArrayList<String> rows;
    private int columns;

    /**
     * Construct an emptry grid. Warning, clearAndSetColumns must be called
     * before calling newRow if this constructor is used.
     */
    public Grid() {
    }

    /**
     * Construct a grid with the same number of columns as there are entries
     * in the list passed in. These entries will be set as the column headers.
     */
    public Grid(ArrayList<String> headers) {
    }

    /**
     * Empties the list of rows, resets the headers list, and resets columns
     * to zero.
     */
    private void clear() {
    }

    /**
     * Clears all data from the table and resets column count, and header
     * titles.
     */
    public void clearAndSetColumns(ArrayList<String> headers) {
    }

    /**
     * Adds a copy of the passed in list of strings to the list of rows. If
     * the number of entries in the row exceeds the number of columns
     * in the table an exception is thrown.
     */
    public void addRow(ArrayList<String> stringList) {
    }

}