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

public class NoticesReport implements IReportDirector, Ivisitor {
	private ReportBuilder builder;
	private Set<String> possibleSizes;
	private Map<String,ArrayList<Integer>> productGroup_unit;
	
	public NoticesReport(){
		possibleSizes = new HashSet<String>();
		productGroup_unit = new HashMap<String,ArrayList<Integer>>();
	}
	
	public ReportBuilder getBuilder(){
		return null;
	}
	

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
	}

	@Override
	public void constructReport() {
		builder.addHeader("Notices");
		builder.addHeading("3-Month Supply Warnings");
		this.printErrors();
		builder.addTextBlock("END");
	}
	
	private void printErrors(){
		StorageUnitVault suv = StorageUnitVault.getInstance();		
		List<StorageUnit> storageUnits = suv.findAll("Deleted = %o", false);
		for(StorageUnit storageUnit : storageUnits){
			storageUnit.accept(this);
		}
	}


	public void visit(Item item) {
		return;
	}

	
	//Add to hashmap with key 
	//ProductGroupId - Unit => count
	public void visit(Product product) {
		if(!possibleSizes.contains(product.getSize().getUnit()))
			possibleSizes.add(product.getSize().getUnit());
		//If a product of the same "size unit" has already been found
		String key = product.getProductContainerId()+product.getSize().getUnit();
		if(productGroup_unit.containsKey(key)){
			productGroup_unit.get(key).add(product.getId());
		} else {
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			tempList.add(product.getId());
			productGroup_unit.put(key, tempList);
		}
		
	}

	//Pass up all counts to parent container
	public void visit(ProductGroup productGroup) {
		ProductVault pv = ProductVault.getInstance();
		
		Boolean incostitentPrinted = false;
		
		for(String possibleSize : possibleSizes){
			String key = productGroup.getId() + possibleSize;
			String parentKey = productGroup.getParentIdString() + possibleSize;
			
			
			if(productGroup_unit.containsKey(key)){
				//Move up everything into parent
				if(productGroup_unit.containsKey(parentKey))
					productGroup_unit.get(parentKey).addAll(productGroup_unit.get(key));
				else
					productGroup_unit.put(parentKey, productGroup_unit.get(key));
				
				//If current size != current pg.size then print it
				if(!possibleSize.equals(productGroup.get3MonthSupply().getUnit())){
					if(incostitentPrinted == false){
						builder.addTextBlock(
								"Product group "+
								productGroup.getStorageUnit().getName()+
								"::"+
								productGroup.getName() +
								" has a 3-month supply ("+
								productGroup.get3MonthSupply().toString()+
								") that is inconsistent with the following products:"
								);
					}
					
					incostitentPrinted = true;
					
					for(int i : productGroup_unit.get(key)){
						builder.addTextBlock(
								"- "+
								productGroup.getName()+
								"::"+
								pv.get(i).getDescription()+
								"(size: "+
								pv.get(i).getSize().toString()+
								")"
								);
					}
					
				}
			}
		}		
	}

	public void visit(StorageUnit storageUnit) {
		productGroup_unit.clear();
	}
}
