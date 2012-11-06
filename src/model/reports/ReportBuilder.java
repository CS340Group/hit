package model.reports;

public interface ReportBuilder {
	
	public void addHeader(String header);
	
	public void addHeading(String heading);

	public void addTextBlock(String text);

	public void startTable(int columns);
	
	public void addRow(String[] row);
	
	public void endTable();
	
}
