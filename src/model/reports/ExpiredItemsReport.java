package model.reports;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ch.lambdaj.Lambda.*;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductGroup;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ExpiredItemsReport implements IReportDirector {
	private ReportBuilder builder;

	
	public ReportBuilder getBuilder(){
		return builder;
	}
	

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
		
	}

	@Override
	public void constructReport() {
		ArrayList<Item> items = ItemVault.getInstance().findAll("IsExpired = %o", true);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
        builder.addHeader("Expired Items");
        builder.startTable(6);
        builder.addRow(new String[] {"Description", "Storage Unit", "Product Group", "Entry Date", "Expire Date", "Item Barcode"});
        for(Item item : items){
            builder.addRow(new String[]{
                    item.getProduct().getDescription(),
                    item.getProduct().getStorageUnit().getName(),
                    item.getProduct().getContainerName(),
                    fmt.print(item.getEntryDate().toDateMidnight()),
                    fmt.print(item.getExpirationDate().toDateMidnight()),
                    item.getBarcodeString()
            });
        }
        builder.endTable();
        builder.endFile();
	}
}
