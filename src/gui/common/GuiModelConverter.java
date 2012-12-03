package gui.common;

import gui.item.ItemData;
import gui.product.ProductData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.item.Item;
import model.product.Product;

public class GuiModelConverter {

	/**
	 * Takes a list of Products, and wraps them in ProducData classes, returning a list.
	 */
	public static List<ProductData> wrapProducts(List<Product> products) {
		ArrayList<ProductData> l = new ArrayList<ProductData>();
		for (Product p : products) {
			l.add(wrapProduct(p));
		}
		return l;
	}

	/**
	 * Takes a list of Items, and wraps them in ItemData classes, returning a list.
	 */
	public static List<ItemData> wrapItems(List<Item> items) {
		ArrayList<ItemData> l = new ArrayList<ItemData>();
		for (Item i : items) {
			l.add(wrapItem(i));
		}
		return l;
	}

	/**
	 * Converts the data from a Product to a ProductData.
	 */
	public static ProductData wrapProduct(Product p) {
		ProductData pData = new ProductData();
		pData.setDescription(p.getDescription());
		pData.setSize(p.getSize().toString());
		pData.setCount(Integer.toString(p.getItemCount()));
		pData.setShelfLife(Integer.toString(p.getShelfLife()));
		pData.setSupply(Integer.toString(p.get3MonthSupply()));
		pData.setBarcode(p.getBarcodeString());
		pData.setTag(p.getId());
		return pData;
	}

	/**
	 * Converts the data from an Item to an ItemData.
	 */
	public static ItemData wrapItem(Item i) {
		ItemData iData = new ItemData();
		iData.setEntryDate(i.getEntryDate().toDate());
        try {
            iData.setExpirationDate(i.getExpirationDate().toDate());
        } catch (NullPointerException e) {
            // If the expiration date is not set, don't add it to the ItemData.
            iData.setExpirationDate(null);
        }
        iData.setBarcode(i.getBarcode().toString());
		iData.setStorageUnit(i.getProductStorageUnitName());
		iData.setProductGroup(i.getProductProductGroupName());
		iData.setTag(i.getId());
		return iData;
	}

}
