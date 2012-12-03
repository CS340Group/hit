package model.reports;

import java.util.ArrayList;

/**
 * This is the interface for a printable report object.
 * The report object consists of 3 difference objects:
 * - {@link model.reports.IPrintObject.IPrintObjectHeader headers}
 * - {@link model.reports.IPrintObject.IPrintObjectTextBlock text-blocks}
 * - {@link model.reports.IPrintObject.IPrintObjectTable tables}
 * <p/>
 * The PrintObject will reference these object by the order in which they are built by the builder.
 * The index starts at 0
 * <p/>
 * This means that getHeader(1) will return the second header built by the builder.
 */

public interface IPrintObject {

    public IPrintObjectHeader getHeader(int position);

    public ArrayList<IPrintObjectHeader> getHeaders();

    public IPrintObjectTextBlock getTextBlock(int position);

    public ArrayList<IPrintObjectTextBlock> getTextBlocks();

    public IPrintObjectTable getTable(int position);

    public ArrayList<IPrintObjectTable> getTables();

    public interface IPrintObjectHeader {
        public String toString();
    }

    public interface IPrintObjectTextBlock {
        public String toString();
    }

    public interface IPrintObjectTable {
        public String getCell(int row, int column);

        public void addRow(String[] row);

        public int rowCount();
    }
}
