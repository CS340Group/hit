package model.reports;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductGroup;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;

import java.util.*;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

public class NSupplyReport implements IReportDirector, Ivisitor {
    private ReportBuilder builder;
    private Map<String, Double> pgCounts;
    private Set<String> possibleSizes;
    int months;

    public NSupplyReport(int months) {
        pgCounts = new HashMap<String, Double>();
        possibleSizes = new HashSet<String>();
        this.months = months;
    }

    public ReportBuilder getBuilder() {
        return null;
    }


    public void setBuilder(ReportBuilder reportBuilder) {
        builder = reportBuilder;

    }

    public void constructReport() {
        builder.addHeader(months + "-Month Supply Report");
        builder.addHeader("Products");
        this.SetUpProductGrid();
        builder.addHeader("Product Groups");
        this.SetUpProductGroupGrid();
        builder.endFile();
        return;

    }

    private void SetUpProductGrid() {
        builder.startTable(4);
        builder.addRow(new String[]{"Description", "Barcode", "3-Month Supply", "Current Supply"});

        ProductVault pv = ProductVault.getInstance();
        ItemVault iv = ItemVault.getInstance();

        List<Product> products = pv.findAll("Deleted = %o", false);
        //Order by description
        products = sort(products, on(Product.class).getDescription());
        List<Item> items = new ArrayList<Item>();
        Product prevProduct = null;
        for (Product product : products) {
            items.addAll(iv.findAll("ProductId = %o", product.getId()));
            //There are duplicate products throughout the system, need to group those
            //before adding the row
            if (prevProduct != null &&
                    prevProduct.getBarcodeString().equals(product.getBarcodeString())
                    ) {
                //Do Nothing
            } else {
                //Process Items
                String description = product.getDescription();
                String barcode = product.getBarcodeString();
                String monthSupply = Integer.toString(product.get3MonthSupply());
                String currentSupply = Integer.toString(items.size());
                builder.addRow(new String[]{description, barcode, monthSupply, currentSupply});
                items.clear();
            }

            prevProduct = product;
        }

        builder.endTable();
    }

    private void SetUpProductGroupGrid() {
        StorageUnitVault suv = StorageUnitVault.getInstance();
        builder.startTable(4);
        builder.addRow(new String[]{"Product Group", "Storage Unit", months + "-Month Supply",
                "Current Supply"});

        List<StorageUnit> storageUnits = suv.findAll("Deleted = %o", false);
        for (StorageUnit storageUnit : storageUnits) {
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
        String key = product.getProductContainerId() + product.getSize().getSizeType();
        double additionalSupply =
                product.getCurrentSupply() * product.getSize().getStandardizedCount();
        if (pgCounts.containsKey(key)) {
            pgCounts.put(key, pgCounts.get(key) + additionalSupply);
        } else {
            pgCounts.put(key, additionalSupply);
        }

    }


    //Pass up all counts to parent container
    public void visit(ProductGroup productGroup) {
        possibleSizes.add(productGroup.get3MonthSupply().getSizeType());

        //Pass all the counts up to the parent
        //If current productGroup-currentUnit < 3monthsupply
        for (String possibleSize : possibleSizes) {
            String key = productGroup.getId() + possibleSize;
            String parentKey = productGroup.getParentIdString() + possibleSize;
            if (pgCounts.containsKey(key)) {
                if (pgCounts.containsKey(parentKey))
                    pgCounts.put(parentKey, pgCounts.get(key) + pgCounts.get(parentKey));
                else
                    pgCounts.put(parentKey, pgCounts.get(key));
            }

            //If this product groups current supply is less than it's 3 month supply
            if (possibleSize.equals(productGroup.get3MonthSupply().getSizeType())) {
                int nsupply =
                        (int) (productGroup.get3MonthSupply().getStandardizedCount() / 3 * months);
                if (pgCounts.containsKey(key)) {
                    if (pgCounts.get(key) < nsupply) {
                        double standardSize = pgCounts.get(key);
                        standardSize = getUnstardizedCount(standardSize,
                                productGroup.get3MonthSupply().getUnit());
                        String unit = productGroup.get3MonthSupply().getUnit();

                        builder.addRow(new String[]{


                                productGroup.getName(),
                                productGroup.getStorageUnit().getName(),
                                Integer.toString(nsupply) + " " + unit,
                                Double.toString(standardSize) + " " + unit}
                        );
                    }
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

    public double getUnstardizedCount(double amount, String type) {
        type = type.toLowerCase();
        if (type.equals("count"))
            return amount;
        if (type.equals("pounds"))
            return amount;
        if (type.equals("ounces"))
            return amount * 16;
        if (type.equals("grams"))
            return amount * 453.592;
        if (type.equals("kilograms"))
            return amount * 0.453592;

        if (type.equals("gallons"))
            return amount;
        if (type.equals("quarts"))
            return amount * 4;
        if (type.equals("fluid ounces"))
            return amount * 128;
        if (type.equals("liters"))
            return amount * 3.78541;
        if (type.equals("pints"))
            return amount * 8;

        return 1;
    }
}
