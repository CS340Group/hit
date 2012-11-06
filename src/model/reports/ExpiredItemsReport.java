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

public class ExpiredItemsReport implements IReportDirector {
	private ReportBuilder builder;

	
	public ReportBuilder getBuilder(){
		return null;
	}
	

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
		
	}

	public void visit(StorageUnit storageUnit) {
		return;
	}

	@Override
	public void constructReport() {
		// TODO Auto-generated method stub
		
	}
}
