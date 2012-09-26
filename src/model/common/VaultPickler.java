package model.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class VaultPickler {
	public AllVaults allVaults;
	
	public VaultPickler(){
		allVaults = new AllVaults();
	}
	
	public void SerializeMe(){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("myvaults.ser");
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
		    File file = new File("myvaults.ser");
		    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		    // Deserialize the object
		    allVaults = (AllVaults) in.readObject();
		    in.close(); 
		    
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
	}
}