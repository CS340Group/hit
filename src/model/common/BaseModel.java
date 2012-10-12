package model.common;

import common.Result;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.*;

import java.util.ArrayList;

/**
 * This class is the controller for the complicated actions to be done with the
 * model data. Here is where you can do all of your adding, deleting, moving 
 * etc...
 */
public class BaseModel {
	public transient  ItemVault itemVault = ItemVault.getInstance();
	public transient  ProductVault productVault = ProductVault.getInstance();
	public transient  StorageUnitVault storageUnitVault = StorageUnitVault.getInstance();
	public transient  ProductGroupVault productGroupVault = ProductGroupVault.getInstance();
	public VaultPickler vp = new VaultPickler();
	
    /**
     * Moves an item from one storage unit to another.
     */
	public Result MoveItem(StorageUnit targetSU, ProductGroup targetPG, Item item){
        assert targetSU != null;
        assert targetPG != null;
        assert item != null;
		RemoveItem(item);
        return AddItem(targetSU, targetPG, item);
	}

    /**
     * Adds an Item to a StorageUnit and ProductGroup.
     */
	public Result AddItem(StorageUnit targetSU, ProductGroup targetPG, Item item){
        assert targetSU != null;
        assert targetPG != null;
        assert item != null;
        Product p = item.getProduct();
        assert p != null : "Product should be in vault";

        if(p.getStorageUnitId() != targetSU.getId()){
            Product p2 = new Product(p);
            p2.setContainerId(targetPG.getId());
            p2.setStorageUnitId(targetSU.getId());
            assert p2.validate().getStatus();
            p2.save();
            p = p2;
        }

        item.setProductId(p.getId());
        Result r = item.validate();
        if(!r.getStatus())
            return r;

        return item.save();
	}

    /**
     * Removes an Item from its StorageUnit and ProductGroup.
     */
    public Result RemoveItem(Item item){
        assert item != null;
		return item.delete();
	}

    /**
     * Moves a Product from one StorageUnit / ProductGroup to another.
     */
    public Result MoveProduct(StorageUnit targetSU, ProductContainer targetPC,
    Product product){
        assert targetSU != null;
        assert targetPC != null;
        assert product != null;
		ArrayList<Product> products = productVault.findAll("Barcode = " + product.getBarcode());
        Product p = null;

        for(Product p2 : products){
            if(p2.getStorageUnitId() == targetSU.getId())
                p = p2;
        }

        if(p == null){
            //Target Product does not exist
            p = product;
            p.setContainerId(targetPC.getId());
            p.setStorageUnitId(targetSU.getId());
            p.validate();
            p.save();
        }

        //Move items
        ArrayList<Item> items = itemVault.findAll("ProductId = " + product.getId());

        for(Item item : items){
            item.setProductId(p.getId());
            assert item.validate().getStatus() && item.save().getStatus();
        }

        //Should we delete the product?
        if(product.isDeleteable().getStatus())
            product.delete();

        return new Result(true);
	}

    /**
     * Deletes a Product from the vaults.
     */
	public Result DeleteProduct(Product product){
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
	public Result Serialize(){
		return vp.SerializeMe();
	}

    /**
     * Reads the serialized data from a file on disk into the vaults.
     */
	public Result Deserialize(){
		return vp.DeSerializeMe();
	}
	
	/*
	 * 
	 */
}