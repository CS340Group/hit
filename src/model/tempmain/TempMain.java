package model.tempmain;
import model.common.Barcode;

public class TempMain {
	public static void main(final String[] args) {
		Barcode foo = Barcode.newFromId("03600029145");
		// foo = Barcode.fromId("0360002914591024");
		foo = new Barcode();
		foo.setCode("036000291452");
		System.out.println(foo.validate().getMessage());
		foo.setCode("036000291458");
		System.out.println(foo.validate().getMessage());
	}
}
