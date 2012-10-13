package gui.common;

import java.util.ArrayList;

import gui.item.ItemData;
import gui.product.ProductData;
import model.item.Item;
import model.product.Product;

public interface IGuiModelConverter {
	
	/**
	 * Wraps products in their productData classes, returning an array.
	 * @param items
	 * @return
	 */
	public ProductData[] wrapProducts(ArrayList<Product> products);
	
	/**
	 * Wraps items in their itemData classes, returning an array.
	 * @param items
	 * @return
	 */
	public ItemData[] wrapItems(ArrayList<Item> items);
	
}
