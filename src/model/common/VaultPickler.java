package model.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Result;


/**
 * Responsible for pickling the data, and writing it out to a file on disk. Also responsible for
 * reading from a file back into memory.
 */
public class VaultPickler {
	public AllVaults allVaults;
	
	/**
	 * Constructor.
	 */
	public VaultPickler(){
		allVaults = new AllVaults();
	}
	
	/**
	 * Saves out the serialized data to disk.
	 */
	public Result SerializeMe(){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("myvaults.ser");
	         ObjectOutputStream out =
	                            new ObjectOutputStream(fileOut);
	         
	         allVaults.itemVault.isSaved = true;
	         out.writeObject(allVaults);
	         out.close();
	         fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		return new Result(true);
	}

	/**
	 *  Reads in the serialized data from disk.
	 */
	public Result DeSerializeMe(){
		try {
		    // Deserialize from a file
		    File file = new File("myvaults.ser");
		    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		    // Deserialize the object
		    allVaults = (AllVaults) in.readObject();
		    allVaults.UseDataFromTheseVaults();
		    in.close(); 
		    
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		return new Result(true);
	}
}