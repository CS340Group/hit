package model.tempmain;

import java.io.IOException;
import model.common.Barcode;
import common.Result;
import common.BarcodePdf;
import java.io.File;

public class TempMain {
	public static void main(final String[] args) {

		Barcode foo = new Barcode();
		Result r = foo.setCode("036000291452");
		r = foo.validate();
		String fileloc = "/Users/murphyra/spam.pdf";
		BarcodePdf d = new BarcodePdf(fileloc);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.addBarcode(foo);
		d.finish();
		try{
			java.awt.Desktop.getDesktop().open(new File(fileloc));
		}catch(IOException e){
			System.out.println("The file was not located on disk.");
		}
	}
}
