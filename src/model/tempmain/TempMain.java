package model.tempmain;

import java.io.FileNotFoundException;
import java.io.IOException;
import common.Result;
import common.BarcodePdf;
import java.io.File;

import org.joda.time.DateTime;

import com.itextpdf.text.DocumentException;

import model.common.Barcode;
import model.common.Size;
import model.item.Item;
import model.product.Product;
import model.productcontainer.StorageUnit;

public class TempMain {
	public static void main(final String[] args) {

        StorageUnit su = new StorageUnit();
        su.setName("Test");
        su.validate();
        su.save();

        Product product = new Product();
        product.setDescription("Spam and Eggs");
        product.set3MonthSupply(3);
        product.setBarcode(new Barcode("1323"));
        product.setContainerId(su.getId());
        product.setCreationDate(new DateTime());
        product.setShelfLife(2);
        product.setSize(new Size(3, Size.Unit.oz));
        product.setStorageUnitId(su.getId());
        Result valid = product.validate();
        Result saved = product.save();
       
        Item item = new Item();
        item.setProductId(product.getId());
        item.setEntryDate(new DateTime());
        item.setExitDate(new DateTime());
        item.setExpirationDate(new DateTime().plusMonths(4)); 
        valid = item.validate();
        saved = item.save();
		
		String fileloc = "/Users/murphyra/spam.pdf";
		try {
			BarcodePdf d;
			d = new BarcodePdf(fileloc);
			d.addItem(item);
//			for(int i=0; i<5; i++){
//			 	d.addItem(item);
//			}
			d.finish();
			try{
				java.awt.Desktop.getDesktop().open(new File(fileloc));
			}catch(IOException e){
				System.out.println("The file was not located on disk.");
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
