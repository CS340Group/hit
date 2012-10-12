package common;

import java.io.FileOutputStream;
import java.io.IOException;

import org.joda.time.LocalDate;

import model.item.Item;

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


public class BarcodePdf{

	private Document _d;
	private PdfWriter _writer;
	private Boolean _open;
	private PdfContentByte _rawContent;
	private PdfPTable _table;


	public BarcodePdf(String fname){

		_d = new Document();
		
		try{
			_writer = PdfWriter.getInstance(_d, new FileOutputStream(fname));
			
			_d.setMargins(25, 25, 25, 25);
			_d.open();
			
			_open = true;
			_rawContent = _writer.getDirectContent();
			
			_table = new PdfPTable(4);
			_table.setHorizontalAlignment(Element.ALIGN_CENTER);
			
		} catch (DocumentException dexc){
			System.out.println("A new document could not be created.");
		} catch (IOException e) {
			System.out.println("Sorry, that's not a valid file path.");
		}

	}
	
	public Result addItem(Item item){
		if (!_open){
			return new Result(false, "Cannot add a product to an unopened file.");
		}
//		if (!product.isValid()){
//			return new Result(false, "Cannot print an invalid product.");
//		}

		// Generate the barcode image:
		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(com.itextpdf.text.pdf.Barcode.UPCA);
		codeEAN.setCode(item.getBarcode().toString());
		Image bcimage = codeEAN.createImageWithBarcode(_rawContent, null, null);
		
		// Add some text to a chunk, and the image to a chunk.
		Font font = new Font(Font.FontFamily.HELVETICA, 8);
		Chunk c1 = new Chunk(item.getProductDescription() + "\n" + item.getShortEntryDateString() + " -> " + item.getShortExpirationDateString() + "\n\n");
		c1.setFont(font);
		Phrase p = new Phrase(c1);
		Chunk c2 = new Chunk(bcimage, 0, 0);
		p.add(c2);

		PdfPCell cell = new PdfPCell(p);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		cell.setBorder(0);
		_table.addCell(cell);

		return new Result(true, "Barcode added successfully.");
	}
	
	public void finish(){
		if (_open){
			try {
				_d.add(_table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_d.close();
			_open = false;
		}
	}
}