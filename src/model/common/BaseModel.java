package model.common;

import common.Result;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.*;

import java.util.ArrayList;


public class BaseModel {
	public transient  ItemVault itemVault = ItemVault.getInstance();
	public transient  ProductVault productVault = ProductVault.getInstance();
	public transient  StorageUnitVault storageUnitVault = StorageUnitVault.getInstance();
	public transient  ProductGroupVault productGroupVault = ProductGroupVault.getInstance();
	
	public Result MoveItem(StorageUnit targetSU, ProductGroup targetPG, Item item){
		RemoveItem(item);
        return AddItem(targetSU, targetPG, item);
	}

	public Result AddItem(StorageUnit targetSU, ProductGroup targetPG, Item item){
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

    public Result RemoveItem(Item item){
		return item.delete();
	}

	public Result MoveProduct(StorageUnit targetSU, ProductGroup targetPC, Product product){
		ArrayList<Product> products = productVault.findAll("Barcode = " + product.getBarcode());
        Product p = null;

        for(Product p2 : products){
            if(p2.getStorageUnitId() == targetSU.getId())
                p = p2;
        }

        if(p == null){
            //Target Product does not exist
            p = new Product(product);
            p.setStorageUnitId(targetSU.getId());
            p.setContainerId(targetPC.getId());
            assert p.validate().getStatus();
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

	public Result DeleteProduct(Product product){
		if(product.isDeleteable().getStatus())
            product.delete();
        else
            return product.isDeleteable();
        return new Result(true);
	}
	
	public Result Serialize(){
		return null;
	}

	public Result Deserialize(){
		return null;
	}
}