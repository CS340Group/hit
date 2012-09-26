package model.common;

import java.io.Serializable;

import model.item.ItemVault;
import model.product.ProductVault;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnitVault;

/**
 * This object encapsulates all of the vaults for ease of use when serializing.
 */
public class AllVaults implements Serializable {
	public ItemVault itemVault;
	public ProductVault productVault;
	public StorageUnitVault storageUnitVault;
	public ProductGroupVault productGroupVault;

	/**
	 * This method gets instances of all vaults for ease of serializing.
	 */
	public AllVaults(){
		itemVault = ItemVault.getInstance();
		storageUnitVault = StorageUnitVault.getInstance();
		productVault = ProductVault.getInstance();
		productGroupVault = ProductGroupVault.getInstance();
	}
}
