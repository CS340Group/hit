package model.common;

import common.Result;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductContainer;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;



public class BaseModel {
	public transient  ItemVault itemVault = ItemVault.getInstance();
	public transient  ProductVault productVault = ProductVault.getInstance();
	public transient  StorageUnitVault storageUnitVault = StorageUnitVault.getInstance();
	public transient  ProductGroupVault productGroupVault = ProductGroupVault.getInstance();
	
	public Result MoveItem(StorageUnit targetSU, ProductContainer targetPC, Item item){
		return null;
		
	}
	public Result AddItem(StorageUnit targetSU, Item item){
		return null;
	}
	public Result RemoveItem(Item item){
		return null;
	}
	public Result MoveProduct(StorageUnit targetSU, ProductContainer targetPC, Item item){
		return null;
	}
	public Result DeleteProduct(Product product){
		return null;
	}
	
	public Result Serialize(){
		return null;
	}
	public Result Deserialize(){
		return null;
	}
}