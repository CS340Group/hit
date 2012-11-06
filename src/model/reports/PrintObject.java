package model.reports;

import java.util.ArrayList;

public class PrintObject implements IPrintObject {
	ArrayList<IPrintObjectHeader> headers = new ArrayList<IPrintObjectHeader>(); 
	ArrayList<IPrintObjectTextBlock> textBlocks = new ArrayList<IPrintObjectTextBlock>(); 
	ArrayList<IPrintObjectTable> tables = new ArrayList<IPrintObjectTable>(); 
	
	public void addHeader(String data){
		headers.add(new PrintObjectHeader(data));
	}
	public void addTextBlock(String data){
		textBlocks.add(new PrintObjectText(data));
	}
	public void addTable(){
		tables.add(new PrintObjectTable());
	}
	
	public int getHeadersSize() {
		return headers.size();
	}
	public int getTextsSize() {
		return textBlocks.size();
	}
	public int getTablesSize() {
		return tables.size();
	}
	
	
	public IPrintObjectHeader getHeader(int position) {
		return headers.get(position);
	}

	public ArrayList<IPrintObjectHeader> getHeaders() {
		return headers;
	}

	public IPrintObjectTextBlock getTextBlock(int position) {
		return textBlocks.get(position);
	}

	public ArrayList<IPrintObjectTextBlock> getTextBlocks() {
		return textBlocks;
	}

	public IPrintObjectTable getTable(int position) {
		return tables.get(position);
	}

	public ArrayList<IPrintObjectTable> getTables() {
		return tables;
	}
	
	public class PrintObjectTable implements IPrintObjectTable{
		private ArrayList<String[]> table;
		public PrintObjectTable(){
			table = new ArrayList<String[]>();
		}
		
		
		public String getCell(int row, int column){
			return  table.get(row)[column];
		}
		public void setCell(int row, int column, String data){
			table.get(row)[column] = data;
		}
		public void addRow(String[] rowData){
			table.add(rowData);
		}
	}
	
	public class PrintObjectText implements IPrintObjectTextBlock{
		private String textBlock;
		public PrintObjectText(String data){
			this.textBlock = data;
		}
		
		public String toString(){
			return textBlock;
		}
		public void setTextBlock(String data){
			textBlock = data;
		}
	}

	
	public class PrintObjectHeader implements IPrintObjectHeader{
		private String header;
		public PrintObjectHeader(String data){
			header = data;
		}
		
		public String toString(){
			return header;
		}
		public void setTextBlock(String data){
			header = data;
		}
	}
	
	
}

