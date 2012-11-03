package model.product;

import java.util.ArrayList;
import org.joda.time.DateTime;

import model.common.IModel;
import model.common.Vault;
import common.Result;

/**
 * The ProductVault class provides a way to query for Products within the select data backend
 * <PRE>
 * ProductVault.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductVault extends Vault{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ProductVault currentInstance;
	
	/**
	 * Private constructor, for the singleton design pattern.
	 */	
	private ProductVault(){
		currentInstance = this;
	}
	
	/**
	 * Returns a reference to the only instance allowed for this class.
	 */
	public static synchronized ProductVault getInstance(){
		if(currentInstance == null) currentInstance = new ProductVault();
		return currentInstance;
	}

	/**
	 * Returns just one item based on the query sent in. 
	 * If you need more than one item returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each Product
	 * @param value What value does the column have
	 * 
	 */
	public  Product find(String query)  {
		return (Product)findPrivateCall(query);
	}
	
	
	/**
	 * Returns a list of Products which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public ArrayList<Product> findAll(String query) {
		return (ArrayList)this.findAllPrivateCall(query);
	}
	public Product get(int id){
		return (Product) this.getPrivateCall(id);
	}
	protected Product getNewObject(){
		return new Product();
	}
	protected Product getCopiedObject(IModel model){
		return new Product((Product)model);
	}
	
	/**
	 * Checks if a new model will fit in the vault
	 * - Product must have unique barcode within a su
	 * 
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected  Result validateNew(Product model){
		Result result = new Result();
		
		//Check that the new product is not a duplicate
		//in the storage container
		result = validateUniqueBarcode(model);
		if(result.getStatus() != true)
			return result;
		result = validateCreationDate(model);
		if(result.getStatus() != true)
			return result;
		
        model.setValid(true);
		return new Result(true);
	}
	

	/**
	 * Checks if the updated model will fit into the vault
	 * -- Does so by deleting the old model, 
	 * -- doing a validate with the new model
	 * -- then undelete the model
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected  Result validateModified(Product model){ 
		assert(model!=null);
        assert(!dataVault.isEmpty());
		
	    model.setValid(true);
        return new Result(true);
	}
	
	/**
	 * Adds the product to the map if it's new.  Should check before doing so.
	 * 
	 * @param model Product to add
	 * @return Result of request
	 */
	protected  Result saveNew(Product model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if(dataVault.isEmpty())
            id = 0;
        else
            id = (int)dataVault.lastKey()+1;

        model.setId(id);
        //If the creation date hasn't been set
        if(model.getCreationDate() == null)
        	model = this.setCreationDate(model);
        model.setSaved(true);
        this.addModel(new Product(model));
        return new Result(true);
	}	
	private  Result validateUniqueBarcode(Product model){
		ArrayList<Product> allProducts = findAll("StorageUnitId = "+model.getStorageUnitId());
		String barcode = model.getBarcode().toString();
		for(Product testProd : allProducts){
			if(testProd.getBarcode().toString().equals(barcode))
				return new Result(false,"Duplicate product in container");
		}
		return new Result(true);
	}
	private Result validateCreationDate(Product model){
		Product existingProduct = find("BarcodeString = "+model.getBarcodeString());
		if(existingProduct != null && existingProduct.getCreationDate() != model.getCreationDate())
			return new Result(false,"The creation date does now match the existing creation date");
		return new Result(true);
	}
	private Product setCreationDate(Product model){
		Product existingProduct = find("BarcodeString = "+model.getBarcodeString());
		if(existingProduct != null && existingProduct.getCreationDate() != model.getCreationDate())
			model.setCreationDate(existingProduct.getCreationDate());
		else
			model.setCreationDate(new DateTime());
		return model;
	}

	/**
	 * Determines if a product with the specified barcode string is in the vault.
	 * @param barcode
	 * @return
	 */
	public boolean hasProductWithBarcode(String barcode) {
		if (this.find("Barcode = " + barcode) == null){
			return false;
		}else {
			return true;
		}
	}
}
