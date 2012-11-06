package gui.reports;

import java.util.ArrayList;
import java.util.HashMap;

import model.reports.IPrintObject;


public class TestPrintObject implements IPrintObject {
    private ArrayList<TestPrintObjectHeader> headers;
    private ArrayList<TestPrintObjectTextBlock> textBlocks;
    private ArrayList<TestPrintObjectTable> tables;

    public TestPrintObject(){
        headers = new ArrayList<TestPrintObjectHeader>();
        textBlocks = new ArrayList<TestPrintObjectTextBlock>();
        tables = new ArrayList<TestPrintObjectTable>();
    }

    public IPrintObjectHeader getHeader(int position) {
        return headers.get(position);
    }

    public void addHeader(String header){
        headers.add(new TestPrintObjectHeader(header));
    }

    public ArrayList<IPrintObjectHeader> getHeaders() {
        return new ArrayList<IPrintObjectHeader>(headers);
    }

    public IPrintObjectTextBlock getTextBlock(int position) {
        return textBlocks.get(position);
    }

    public void addTextBlock(String textBlock){
        textBlocks.add(new TestPrintObjectTextBlock(textBlock));
    }

    public ArrayList<IPrintObjectTextBlock> getTextBlocks() {
        return new ArrayList<IPrintObjectTextBlock>(textBlocks);
    }

    public IPrintObjectTable getTable(int position) {
        return tables.get(position);
    }

    public TestPrintObjectTable newTable(){
        TestPrintObjectTable table = new TestPrintObjectTable();
        tables.add(table);
        return table;
    }

    public ArrayList<IPrintObjectTable> getTables() {
        return new ArrayList<IPrintObjectTable>(tables);
    }

    public class TestPrintObjectHeader implements IPrintObjectHeader {
        String contents;

        public TestPrintObjectHeader(String header){
            contents = header;
        }

        @Override
        public String toString(){
            return contents;
        }
    }

    public class TestPrintObjectTextBlock implements IPrintObjectTextBlock {
        String contents;

        public TestPrintObjectTextBlock(String textBlock){
            contents = textBlock;
        }

        @Override
        public String toString(){
            return contents;
        }
    }

    public class TestPrintObjectTable implements IPrintObjectTable {
        HashMap<Integer,HashMap<Integer,String>> contents;

        public TestPrintObjectTable(){
            contents = new HashMap<Integer, HashMap<Integer, String>>();
        }

        public void setCell(int row, int col, String cell){
            if(contents.containsKey(row))
                contents.get(row).put(col,cell);
            else{
                contents.put(row, new HashMap<Integer, String>());
                contents.get(row).put(col,cell);
            }
        }

        @Override
        public String getCell(int row, int col) {
            return contents.get(row).get(col);
        }

		@Override
		public void addRow(String[] row) {
			// TODO Auto-generated method stub
			
		}
    }
}
