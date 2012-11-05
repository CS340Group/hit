package model.reports;

public interface ReportBuilder {
	
	public void addHeader(String header);
	
	public void startTable();
	
	public void addRow(String[] row);
	
	public void endTable();
	
}
