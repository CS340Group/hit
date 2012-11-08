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

public class NSupplyReport implements IReportDirector, Ivisitor {
	private ReportBuilder builder;
	private Map<String, Double> pgCounts;
	private Set<String> possibleSizes;
	int months;
	
	public NSupplyReport(int months){
		pgCounts = new HashMap<String,Double>();
		possibleSizes = new HashSet<String>();
		this.months = months;
	}
	
	public ReportBuilder getBuilder(){
		return null;
	}
	

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
		
	}
	public void constructReport() {
		builder.addHeader(months+"-Month Supply Report");
		builder.addHeader("Products");
		this.SetUpProductGrid();
		builder.addHeader("Product Groups");
		this.SetUpProductGroupGrid();
		builder.endFile();
		return;
		
	}
	private void SetUpProductGrid(){
		builder.startTable(4);
		builder.addRow(new String[] {"Description","Barcode","3-Month Supply","Current Supply"});
		
		ProductVault pv = ProductVault.getInstance();
		ItemVault iv = ItemVault.getInstance();
		
		List<Product> products = pv.findAll("Deleted = %o", false);
		//Order by description
		products = sort(products, on(Product.class).getDescription());
		List<Item> items = new ArrayList<Item>();
		Product prevProduct = null;
		for(Product product : products){
			items.addAll(iv.findAll("ProductId = %o", product.getId()));
			//There are duplicate products throughout the system, need to group those
			//before adding the row
			if( prevProduct != null && 
					prevProduct.getBarcodeString().equals(product.getBarcodeString())
					){
				//Do Nothing
			} else {
				//Process Items
				String description = product.getDescription();
				String barcode = product.getBarcodeString();
				String monthSupply = Integer.toString(product.get3MonthSupply());
				String currentSupply = Integer.toString(items.size());
				builder.addRow(new String[] {description,barcode,monthSupply,currentSupply});
				items.clear();
			}
			
			prevProduct = product;
		}
		
		builder.endTable();
	}
	private void SetUpProductGroupGrid(){
		StorageUnitVault suv = StorageUnitVault.getInstance();
		builder.startTable(4);
		builder.addRow(new String[]{"Product Group","Storage Unit",months+"-Month Supply","Current Supply"});
		
		List<StorageUnit> storageUnits = suv.findAll("Deleted = %o", false);
		for(StorageUnit storageUnit : storageUnits){
			storageUnit.accept(this);
		}
		
		builder.endTable();
	}


	public void visit(Item item) {
		return;
	}

	
	//Add to hashmap with key 
	//ProductGroupId - Unit => count
	public void visit(Product product) {
		//If a product of the same "size unit" has already been found
		String key = product.getProductContainerId()+product.getSize().getUnit();
		if(pgCounts.containsKey(key)){
			pgCounts.put(key, pgCounts.get(key) + product.getSize().getAmount());
		} else {
			pgCounts.put(key, product.getCurrentSupply());
		}
		
	}

	//Pass up all counts to parent container
	public void visit(ProductGroup productGroup) {
		possibleSizes.add(productGroup.get3MonthSupply().getUnit());
		
		//Pass all the counts up to the parent
		//If current productGroup-currentUnit < 3monthsupply
		for(String possibleSize : possibleSizes){
			String key = productGroup.getId() + possibleSize;
			String parentKey = productGroup.getParentIdString() + possibleSize;
			if(pgCounts.containsKey(key)){
				if(pgCounts.containsKey(parentKey))
					pgCounts.put(parentKey, pgCounts.get(key) + pgCounts.get(parentKey) );
				else
					pgCounts.put(parentKey, pgCounts.get(key));
			}
			
			//If this product groups current supply is less than it's 3 month supply
			if(possibleSize.equals(productGroup.get3MonthSupply().getUnit())){
				int nsupply = (int) (productGroup.get3MonthSupply().getAmount()/3*months);
				if(pgCounts.containsKey(key)){
					if(pgCounts.get(key) < nsupply)
						builder.addRow(new String[]{
								productGroup.getName(),
								productGroup.getStorageUnit().getName(),
								Integer.toString(nsupply),
								Double.toString(pgCounts.get(key))}
						);
				} /*else {
					builder.addRow(new String[]{
							productGroup.getName(),
							productGroup.getStorageUnit().getName(),
							Integer.toString(nsupply),
							"0"}
					);
				}*/
			}
		}
				
	}

	public void visit(StorageUnit storageUnit) {
		pgCounts.clear();
		return;
	}
}
