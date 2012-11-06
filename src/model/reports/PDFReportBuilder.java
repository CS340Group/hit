package model.reports;

import com.itextpdf.text.Document;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import common.Result;


public class PDFReportBuilder implements ReportBuilder {

	private Document _d;
	private PdfWriter _writer;
	private Boolean _open;
	private PdfContentByte _rawContent;
	private PdfPTable _table;
	private int _itemsAdded; // Keeps track of how many items have been added, in case we need to fill up dummy items.	
	private boolean _tableStarted;
	private int _columns;
    
    public PDFReportBuilder(){
		_d = new Document();
		
		try {
			_writer = PdfWriter.getInstance(_d, new FileOutputStream("PdfReport.pdf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_d.setMargins(25, 25, 25, 25);
		_d.open();
		
		_open = true;
		_rawContent = _writer.getDirectContent();
    }

	@Override
	public void addHeader(String header) {
		_d.addHeader("Header", header);
	}

	@Override
	public void addHeading(String heading) {
		_d.addTitle(heading);
	}

	@Override
	public void startTable(int columns) {
		_columns = columns;
		_tableStarted = true;
		_table = new PdfPTable(_columns);
		_table.setHorizontalAlignment(Element.ALIGN_CENTER);
		_itemsAdded = 0;
	}

	@Override
	public Result addRow(String[] row) {
		if (!_tableStarted)
			return new Result(false, "The table must be started before adding a row, silly!");
		for(String s : row){
			// Add some text to a chunk, and the image to a chunk.
			Font font = new Font(Font.FontFamily.HELVETICA, 8);
			Chunk c1 = new Chunk(s);
			c1.setFont(font);
			Phrase p = new Phrase(c1);

			PdfPCell cell = new PdfPCell(p);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(10);
			cell.setBorder(0);
			_table.addCell(cell);
			
			_itemsAdded++;
		};
		return new Result(true);
	}

	@Override
	public Result endTable() {
		if (_open){
			fillNullCells();
			try {
				_d.add(_table);
			} catch (DocumentException e) {
				return new Result(false, "The document was not successfully constructed.");
			}
			_d.close();
			_open = false;
		}
		return new Result(true, "The document is closed.");	
	}
		
	/**
	 * This is a convenience function to make it easy to fill up the remaining table cells.
	 */
	private void fillNullCells(){
		int itemsToAdd = _columns - (_itemsAdded % _columns);
		while(itemsToAdd > 0){
			PdfPCell c = new PdfPCell();
			c.setBorder(0);
			_table.addCell(c);
			itemsToAdd--;
		}
	}

	@Override
	public void addTextBlock(String text) {
		// Add some text to a chunk, and the image to a chunk.
		Font font = new Font(Font.FontFamily.HELVETICA, 8);
		Chunk c1 = new Chunk(text);
		c1.setFont(font);
		Phrase p = new Phrase(c1);
		try {
			_d.add(p);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
