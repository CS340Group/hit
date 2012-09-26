package model.common;

import java.io.Serializable;

import model.item.ItemVault;
import model.product.ProductVault;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnitVault;

public class AllVaults implements Serializable {
	public ItemVault itemVault;
	public ProductVault productVault;
	public StorageUnitVault storageUnitVault;
	public ProductGroupVault productGroupVault;
	public AllVaults(){
		itemVault = ItemVault.getInstance();
		storageUnitVault = StorageUnitVault.getInstance();
		productVault = ProductVault.getInstance();
		productGroupVault = ProductGroupVault.getInstance();
	}
}
