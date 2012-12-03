package model.common;

import common.Result;
import static ch.lambdaj.Lambda.*;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the controller for the complicated actions to be done with the
 * model data. Here is where you can do all of your adding, deleting, moving 
 * etc...
 */
public class ModelFacade {
	public transient  ItemVault itemVault = ItemVault.getInstance();
	public transient  ProductVault productVault = ProductVault.getInstance();
	public transient  StorageUnitVault storageUnitVault = StorageUnitVault.getInstance();
	public transient  ProductGroupVault productGroupVault = ProductGroupVault.getInstance();
	public VaultPickler vp = new VaultPickler();
	
    /**
     * Moves an item from one storage unit to another.
     */
	public Result dragItem(ProductContainer targetPC, Item item){
		//Create a new product
		//Add the one item to that product
		//Call the move product method
		Product currentProduct = item.getProduct();
		
		//
		Product newProduct = new Product();
        newProduct.set3MonthSupply(currentProduct.get3MonthSupply());
        newProduct.setBarcode(currentProduct.getBarcode());
        newProduct.setCreationDate(currentProduct.getCreationDate());
        newProduct.setDescription(currentProduct.getDescription());
        newProduct.setShelfLife(currentProduct.getShelfLife());
        newProduct.setSize(currentProduct.getSize());
        
        
        newProduct.setStorageUnitId(-1);
        newProduct.setContainerId(-1);
        newProduct.validate();
        newProduct.save();

        item.setProductId(newProduct.getId());
        item.validate();
        item.save();
        
        ProductGroup possiblePG = this.productGroupVault.get(targetPC.getId());
        if(possiblePG != null)
        	this.dragProduct(possiblePG.getStorageUnit(), targetPC, newProduct);
        else
        	this.dragProduct((StorageUnit)targetPC, targetPC, newProduct);
        return new Result(true);
	}

    /**
     * Adds an Item to a StorageUnit.
     * This is called only when moving items
     * 
     */
	public Result addItem(StorageUnit targetSU, Item item){
        Product currentProduct = item.getProduct();
        assert currentProduct != null : "Product should be in vault";
        assert targetSU != null;
        assert item != null;
        
        //If we are moving an item then this logic is needed,
        //if it's a new item this is skipped
        if(currentProduct.getStorageUnitId() != targetSU.getId()){
        	//Check that a product does not exist already
        	List<Product> possibleExistingProducts;
        	possibleExistingProducts = this.productVault.findAll("StorageUnitId = %o", targetSU.getId());
        	Product existingProduct = null;
    		for(Product tempP : possibleExistingProducts){
    			if(tempP.getBarcodeString().equals(currentProduct.getBarcodeString()))
    				existingProduct = tempP;
    		}
    		
    		//If there isn't a product, create a new one
        	if(existingProduct==null){
	            Product p2 = new Product();
	            p2.set3MonthSupply(currentProduct.get3MonthSupply());
	            p2.setBarcode(currentProduct.getBarcode());
	            p2.setCreationDate(currentProduct.getCreationDate());
	            p2.setDescription(currentProduct.getDescription());
	            p2.setShelfLife(currentProduct.getShelfLife());
	            p2.setSize(currentProduct.getSize());
	
	            
	            p2.setStorageUnitId(targetSU.getId());
	            p2.setContainerId(-1);
	            p2.validate();
	            p2.save();
	            currentProduct = p2;
        	} else{
        		currentProduct = existingProduct;
        	}
        	item.setProductId(currentProduct.getId());
            item.validate();
            
        }

        
        Result r = item.validate();
        if(!r.getStatus())
            return r;

        return item.save();
	}

    /**
     * Removes an Item from its StorageUnit and ProductGroup.
     */
    public Result removeItem(Item item){
        assert item != null;
		return item.delete();
	}

    /**
     * Moves a Product from one StorageUnit / ProductGroup to another.
     */
    public Result dragProduct(StorageUnit targetSU, ProductContainer targetPC,
    Product product){
        assert targetSU != null;
        assert targetPC != null;
        assert product != null;
        

		ArrayList<Product> products = productVault.findAll("Barcode = %o",  product.getBarcode());
        Product p = null;

        for(Product possibleProduct : products){
            if(possibleProduct.getId() != product.getId() && possibleProduct.getStorageUnitId() == targetSU.getId()){
                p = possibleProduct;
                break;
            }
        }
       
        //If there isn't currently a product just change the ID's
        //If there is a product then delete old one and move items
        if(p != null){
        	//For each item in "p" change product Id
            ArrayList<Item> items = itemVault.findAll("ProductId = %o",  p.getId());

            for(Item item : items){
                item.setProductId(product.getId());
                item.validate();
                item.save();
            }
            p.delete();
            p.save();
        }
        //Move the product to a neutral area before moving to the target
        //Helps avoid bugs
        product.setStorageUnitId(-1);
        product.validate();
        product.save();
        
        product.setContainerId(targetPC.getId());
        product.setStorageUnitId(targetSU.getId());
        product.validate();
        product.save();
        return new Result(true);
	}

    /**
     * Deletes a Product from the vaults.
     */
	public Result deleteProduct(Product product){
        assert product != null; 
		if(product.isDeleteable().getStatus())
            product.delete();
        else
            return product.isDeleteable();
        return new Result(true);
	}
	
    /**
     * Serializes the data in the vaults to a file on disk.
     */
	public Result serialize(){
		return vp.SerializeMe();
	}

    /**
     * Reads the serialized data from a file on disk into the vaults.
     */
	public Result deserialize(){
		return vp.DeSerializeMe();
	}
	
	/*
	 * 
	 */
}