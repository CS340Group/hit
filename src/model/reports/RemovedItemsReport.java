package model.reports;

import model.common.AllVaults;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.storage.StorageManager;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

public class RemovedItemsReport implements IReportDirector {
    private ReportBuilder builder;
    DateTime sinceWhen;
    private ItemVault _itemVault = ItemVault.getInstance();

    public RemovedItemsReport(DateTime sinceLastRemovedReport) {
        sinceWhen = new DateTime(sinceLastRemovedReport);

    }

    public ReportBuilder getBuilder() {
        return null;
    }


    public void setBuilder(ReportBuilder reportBuilder) {
        builder = reportBuilder;

    }

    public void constructReport() {
        builder.addHeading("Items Removed Since " + sinceWhen.toString());
        builder.startTable(5);
        builder.addRow(new String[]{"Description", "Size", "Product Barcode", "Removed",
                "Current Supply"});
        ItemVault iv = ItemVault.getInstance();
        AllVaults al = new AllVaults();
        iv.sinceLastRemovedReport = DateTime.now();


        //Update Last Removed for sql
        Item miscStorage = new Item();
        miscStorage.setEntryDate(DateTime.now());
        StorageManager.getInstance().getFactory().getMiscStorageDAO().insert(miscStorage);


        List<Item> items = iv.findAll("ExitDate > %o", sinceWhen, true);
        items = sort(items, on(Item.class).getProductBarcode());

        // Build a set of the products belonging to the removed items.
        HashSet<Product> products = new HashSet<Product>();
        for (Item item : items) products.add(item.getProduct());
        // Flatten the set and sort it.
        List<Product> sortedProducts = new ArrayList<Product>(products);
        sortedProducts = sort(sortedProducts, on(Product.class).getDescription());

        // Build the rows of the report through the products.
        for (Product product : sortedProducts) {
            // Find all items belonging to this product.
            int remaining = product.getItems().size();
            int deleted = product.getDeletedItems().size();

            builder.addRow(new String[]{
                    product.getDescription(),
                    product.getSize().toString(),
                    product.getBarcode(),
                    Integer.toString(deleted),
                    Integer.toString(remaining)});
        }

        builder.endTable();
        builder.endFile();
    }
}
