package model.reports;

import com.itextpdf.text.Document;

public class PDFReportBuilder implements ReportBuilder {

    Document doc;
    public PDFReportBuilder(){
        doc = new Document();
    }

	@Override
	public void addHeader(String header) {
        doc.addTitle(header);
	}

	@Override
	public void addHeading(String heading) {
		doc.addHeader(heading,"");
	}

	@Override
	public void startTable(int columns) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRow(String[] row) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTextBlock(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String returnReport() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
