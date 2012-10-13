package gui.common;

import gui.item.ItemData;
import gui.product.ProductData;

import java.util.ArrayList;

import model.item.Item;
import model.product.Product;

public class GuiModelConverter {

	public static ProductData[] wrapProducts(ArrayList<Product> products) {
		
		int numProducts = products.size();
		ProductData[] pDatas = new ProductData[numProducts];
		
		int count = 0;
		for (Product p : products) {
			pDatas[count] = wrapProduct(p);
		}
		
		return pDatas;
	}

	public static ItemData[] wrapItems(ArrayList<Item> items) {
		
		int numItems = items.size();
		ItemData[] iDatas = new ItemData[numItems];
		
		int count = 0;
		for (Item i : items) {
			ItemData iData = new ItemData();
			iData.setEntryDate(i.getEntryDate().toDate());
			iData.setExpirationDate(i.getExpirationDate().toDate());
			iData.setBarcode(i.getBarcode().toString());
			iData.setStorageUnit(i.getProductStorageUnitName());
			iData.setProductGroup(i.getProductProductGroupName());
			iData.setTag(i.getId());
			iDatas[count] = iData;
		}
		
		return iDatas;
	}

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

}
