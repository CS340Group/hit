package gui.common;

import gui.item.ItemData;
import gui.product.ProductData;

import java.util.ArrayList;

import model.item.Item;
import model.product.Product;

public class GuiModelConverter implements IGuiModelConverter {

	@Override
	public ProductData[] wrapProducts(ArrayList<Product> products) {
		
		int numProducts = products.size();
		ProductData[] pDatas = new ProductData[numProducts];
		
		int count = 0;
		for (Product p : products) {
			ProductData pData = new ProductData();
			pData.setDescription(p.getDescription());
			pData.setSize(p.getSize().toString());
			pData.setCount(p.getItemCount());
			pData.setShelfLife(Integer.toString(p.getShelfLife()));
			pData.setSupply(Integer.toString(p.get3MonthSupply()));
			pData.setBarcode(p.getBarcodeString());
			pData.setTag(p.getId());
			pDatas[count] = pData;
		}
		
		return pDatas;
	}

	@Override
	public ItemData[] wrapItems(ArrayList<Item> items) {
		
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

}
