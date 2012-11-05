package model.reports;

import java.util.ArrayList;
import java.util.Dictionary;
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
	
	public ReportBuilder getBuilder(){
		return null;
	}

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
		
	}

	public void constructReport() {
		builder.addHeader("N Supply Report");
		builder.addHeader("Products");
		this.SetUpProductGrid();
		builder.addHeader("Product Groups");

		
	}
	private void SetUpProductGrid(){
		builder.startTable();
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
	}
	private void SetUpProductGroupGrid(){
		StorageUnitVault suv = StorageUnitVault.getInstance();
		
		List<StorageUnit> storageUnits = suv.findAll("Deleted = %o", false);
		for(StorageUnit storageUnit : storageUnits){
			storageUnit.accept(this);
		}
	}

	@Override
	public void visit(Item item) {
		// TODO Auto-generated method stub
		
	}

	
	//Add to hashmap with key 
	//ProductGroupId - Unit => count
	public void visit(Product product) {
		//If a product of the same "size unit" has already been found
		String key = product.getProductContainerId()+product.getSize().getUnit();
		if(pgCounts.containsKey(key)){
			pgCounts.put(key, pgCounts.get(key) + product.getSize().getAmount());
		} else {
			pgCounts.put(key, (double) product.getSize().getAmount());
		}
		
	}

	//Pass up all counts to parent container
	public void visit(ProductGroup productGroup) {
		possibleSizes.add(productGroup.get3MonthSupply().getUnit());
		
		for(String possibleSize : possibleSizes){
			String key = productGroup.getId() + possibleSize;
			String parentKey = productGroup.getParentIdString() + possibleSize;
			if(pgCounts.containsKey(key)){
				if(pgCounts.containsKey(parentKey))
					pgCounts.put(parentKey, pgCounts.get(key) + pgCounts.get(parentKey) );
				else
					pgCounts.put(parentKey, pgCounts.get(key));
			}
		}
				
	}

	@Override
	public void visit(StorageUnit storageUnit) {
		// TODO Auto-generated method stub
		
	}
}
