package model.reports;

import model.reports.IPrintObject.IPrintObjectTable;
import model.reports.PrintObject.PrintObjectTable;

public class ObjectReportBuilder implements ReportBuilder {
	private PrintObject printObject;
	
	public ObjectReportBuilder(){
		printObject = new PrintObject();
	}
	
	public void addHeader(String header) {
		printObject.addHeader(header);
	}

	public void startTable() {
		printObject.addTable();
	}

	public void addRow(String[] row) {
		int currentTable = -1 + printObject.getTablesSize();
		if(currentTable >= 0){
			IPrintObjectTable table = printObject.getTable(currentTable);
			table.addRow(row);
		}
	}

	public void endTable() {
		// TODO Auto-generated method stub
		
	}

}
