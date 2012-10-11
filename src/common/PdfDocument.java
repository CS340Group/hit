package common;

import java.io.FileOutputStream;
import java.io.IOException;

import model.common.Barcode;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfDocument{

	private Document _d;
	private PdfWriter _writer;
	private Boolean _open;
	private PdfContentByte _rawContent;
	
	public PdfDocument(String fname){
		
		_d = new Document();
		
		try{
			_writer = PdfWriter.getInstance(_d, new FileOutputStream(fname));
			_d.open();
			_open = true;
			_rawContent = _writer.getDirectContent();
		} catch (DocumentException dexc){
			System.out.println("A new document could not be created.");
		} catch (IOException e) {
			System.out.println("Sorry, that's not a valid file path.");
		}
	}
	
	public Result addBarcode(Barcode b){
		if (!_open){
			return new Result(false, "Cannot add a barcode to an unopened file.");
		}
		if (!b.isValid()){
			return new Result(false, "Cannot print an invalid barcode.");
		}

		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(com.itextpdf.text.pdf.Barcode.UPCA);
		codeEAN.setCode(b.toString());
		try {
			_d.add(codeEAN.createImageWithBarcode(_rawContent, null, null));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("I've added a barcode.");

		return new Result(true, "Barcode added successfully.");
	}
	
	public void finish(){
		if (_open){
			_d.close();
			_open = false;
		}
	}
}