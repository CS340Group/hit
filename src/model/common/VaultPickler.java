package model.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



import common.Result;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductContainer;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;



public class VaultPickler {
	public AllVaults allVaults;
	
	public VaultPickler(){
		allVaults = new AllVaults();
	}
	
	public void SerializeMe(){
		try
	      {
	         FileOutputStream fileOut =
<<<<<<< HEAD
	         new FileOutputStream("D:\\_Programs\\xampp\\htdocs\\github\\hit\\src\\myvaults2.ser");
=======
	         new FileOutputStream("myvaults.ser");
>>>>>>> 83dc0c8c1b13af4a79f382803d2c2c10fa7da8c6
	         ObjectOutputStream out =
	                            new ObjectOutputStream(fileOut);
	         out.writeObject(allVaults);
	         out.close();
	         fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	public void DeSerializeMe(){
		try {
		    // Deserialize from a file
<<<<<<< HEAD
		    File file = new File("D:\\_Programs\\xampp\\htdocs\\github\\hit\\src\\myvaults2.ser");
=======
		    File file = new File("myvaults.ser");
>>>>>>> 83dc0c8c1b13af4a79f382803d2c2c10fa7da8c6
		    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		    // Deserialize the object
		    allVaults = (AllVaults) in.readObject();
		    in.close(); 
		    
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
	}
}