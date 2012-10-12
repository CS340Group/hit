package common;

import java.io.FileOutputStream;
import java.io.IOException;

import model.common.Barcode;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.ColumnText;


public class BarcodePdf{

	private Document _d;
	private PdfWriter _writer;
	private Boolean _open;
	private PdfContentByte _rawContent;
	private ColumnText _ct;
	private int _curcol;
	private int _colstatus;
	
    /** Definition of two columns */
    public static final float[][] COLUMNS = {
        { 36, 36, 296, 806 } , { 299, 36, 559, 806 }
    };
	
	public BarcodePdf(String fname){

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

		/* Copied from the example */
        _ct = new ColumnText(_rawContent);
        _ct.setAlignment(Element.ALIGN_JUSTIFIED);
        _ct.setExtraParagraphSpace(6);
        _ct.setLeading(14);
        _ct.setIndent(10);
        _ct.setRightIndent(3);
        _ct.setSpaceCharRatio(PdfWriter.NO_SPACE_CHAR_RATIO);
        _curcol = 0;
        _colstatus = ColumnText.START_COLUMN;
        _ct.setSimpleColumn(
            COLUMNS[_curcol][0], COLUMNS[_curcol][1],
            COLUMNS[_curcol][2], COLUMNS[_curcol][3]);

	}
	
	public Result addBarcode(Barcode b){
		if (!_open){
			return new Result(false, "Cannot add a barcode to an unopened file.");
		}
		if (!b.isValid()){
			return new Result(false, "Cannot print an invalid barcode.");
		}

		// Generate the barcode image:
		BarcodeEAN codeEAN = new BarcodeEAN();
		codeEAN.setCodeType(com.itextpdf.text.pdf.Barcode.UPCA);
		codeEAN.setCode(b.toString());
		Image bcimage = codeEAN.createImageWithBarcode(_rawContent, null, null);

		// Add it to the column:
		_ct.addElement(bcimage);
		try {
			_colstatus = _ct.go();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (ColumnText.hasMoreText(_colstatus)) {
            _curcol = Math.abs(_curcol - 1);
            if (_curcol == 0)
                _d.newPage();
            _ct.setSimpleColumn(
                COLUMNS[_curcol][0], COLUMNS[_curcol][1],
                COLUMNS[_curcol][2], COLUMNS[_curcol][3]);
            _ct.setYLine(COLUMNS[_curcol][3]);
            try {
				_colstatus = _ct.go();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

		// try {
		// 	_d.add(codeEAN.createImageWithBarcode(_rawContent, null, null));
		// } catch (DocumentException e) {
		// 	e.printStackTrace();
		// }
		// System.out.println("I've added a barcode.");

		return new Result(true, "Barcode added successfully.");
	}
	
	public void finish(){
		if (_open){
			_d.close();
			_open = false;
		}
	}
}