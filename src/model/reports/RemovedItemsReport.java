package model.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import static ch.lambdaj.Lambda.*;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductGroup;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;

public class RemovedItemsReport implements IReportDirector {
	private ReportBuilder builder;
	DateTime sinceWhen ;
	
	public RemovedItemsReport(DateTime sinceLastRemovedReport){
		sinceWhen = new DateTime(sinceLastRemovedReport);
		
	}
	
	public ReportBuilder getBuilder(){
		return null;
	}
	

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
		
	}

	public void constructReport() {
		builder.addHeading("Items Removed Since "+sinceWhen.toString());
		builder.startTable(5);
		builder.addRow(new String[] {"Description","Size","Product Barcode","Removed","Current Supply"});
		ItemVault iv = ItemVault.getInstance();
		iv.sinceLastRemovedReport = DateTime.now();
		ProductVault pv = ProductVault.getInstance();
		
		List<Item> items = iv.findAll("ExitDate > %o", sinceWhen,true);
		items = sort(items, on(Item.class).getProductBarcode());
		Item prevItem = null;

		int totalRemoved = 0;
		int currentSupply = 0;
		
		for(Item currentItem : items){
			if(currentItem.getProductBarcode() != prevItem.getProductBarcode()){
				//Print row
				List<Item> allItems = iv.findAll("ProductBarcode() = "+currentItem.getProductBarcode());
				for(Item i : allItems)
					if(i.getExitDate() == null)
						currentSupply++;
				
				
				builder.addRow(new String[]{
						prevItem.getProductDescription(),
						prevItem.getProduct().getSize().toString(),
						prevItem.getProductBarcode(),
						Integer.toString(totalRemoved),
						Integer.toString(currentSupply)});
				
				currentSupply=0;
				totalRemoved=0;
			}
			totalRemoved++;
			
			prevItem = currentItem;
		}
		
		currentSupply = 0;
		
	}
}
