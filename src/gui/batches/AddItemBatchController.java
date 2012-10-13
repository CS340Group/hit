package gui.batches;

import common.BarcodePdf;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import model.common.Barcode;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import org.joda.time.DateTime;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
		IAddItemBatchController {

    ArrayList<ProductData> products = new ArrayList<ProductData>();
    ArrayList<ItemData> items = new ArrayList<ItemData>();
    boolean scanner = true;
    Timer timer;
    ProductContainerData target;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
        getView().setUseScanner(scanner);
        timer = new Timer();
        this.target = target;
		construct();
	}

	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IAddItemBatchView getView() {
		return (IAddItemBatchView) super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
        getView().setProducts(getProducts());
        getView().setItems(getItems());
        getView().setBarcode("");
        getView().setCount("1");
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
        int count = 0;
        try {
            count = Integer.parseInt(getView().getCount());
        } catch(Exception e){
            getView().enableItemAction(false);
            return;
        }
        getView().enableItemAction(
                !getView().getBarcode().isEmpty()
                && count > 0
                && !scanner
        );
	}

	/**
	 * This method is called when the "Entry Date" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void entryDateChanged() {
	}

	/**
	 * This method is called when the "Count" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void countChanged() {
        enableComponents();
	}

	/**
	 * This method is called when the "Product Barcode" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void barcodeChanged() {
        enableComponents();
        try {
            timer.cancel();
            timer = new Timer();
        }
        catch(IllegalStateException e){ }
        if(scanner)
            timer.schedule(new ScannerTimer(), SCANNER_SECONDS);
	}

	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
        scanner = getView().getUseScanner();
	}

	/**
	 * This method is called when the selected product changes
	 * in the add item batch view.
	 */
	@Override
	public void selectedProductChanged() {
	}

	/**
	 * This method is called when the user clicks the "Add Item" button
	 * in the add item batch view.
	 */
	@Override
	public void addItem() {
        Barcode barcode = new Barcode(getView().getBarcode());
        Product product = ProductVault.getInstance().find("Barcode = " + barcode.toString());
        if(product == null){
            getView().displayAddProductView(target);
            product = ProductVault.getInstance().find("Barcode = " + barcode.toString());
        }
        if(product == null){
            loadValues();
            return;
        }

        ProductData pd = findProduct(getView().getBarcode());
        if(pd == null){
            pd = new ProductData();
            pd.setBarcode(getView().getBarcode());
            pd.setCount("0");
            pd.setDescription(product.getDescription());
            pd.setShelfLife(String.valueOf(product.getShelfLife()));
            pd.setSize(product.getSize().toString());
            pd.setSupply(String.valueOf(product.get3MonthSupply()));
            pd.setTag(product.getId());
            products.add(pd);
        }

        for(int i = 0; i < Integer.parseInt(getView().getCount()); i++){
            Item item = new Item();
            item.setEntryDate(new DateTime(getView().getEntryDate()));
            item.setExpirationDate(item.getEntryDate().plusMonths(product.getShelfLife()));
            item.setProductId(product.getId());
            item.validate();
            item.save();

            ItemData id = new ItemData();
            id.setBarcode(item.getBarcode().toString());
            id.setEntryDate(item.getEntryDate().toDate());
            id.setExpirationDate(item.getExpirationDate().toDate());
            try{id.setProductGroup(product.getContainer().getName());}
            catch(NullPointerException e){ id.setProductGroup(""); }
            id.setStorageUnit(product.getStorageUnit().getName());
            id.setTag(item.getId());
            items.add(id);

            int count = Integer.parseInt(pd.getCount())+1;
            pd.setCount(String.valueOf(count));

        }
        loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the add item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view.
	 */
	@Override
	public void done() {
		getView().close();
        if(!items.isEmpty()){
            try{
                BarcodePdf pdf = new BarcodePdf("items.pdf");
                Iterator<ItemData> i = items.iterator();
                while(i.hasNext()){
                    int id = (Integer) i.next().getTag();
                    pdf.addItem(ItemVault.getInstance().get(id));
                }
                pdf.finish();
            } catch (Exception e){
                getView().displayErrorMessage(e.getMessage()    );
            }
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("items.pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        }
	}

    private ProductData[] getProducts(){
        Iterator<ProductData> i = products.iterator();
        ProductData[] p = new ProductData[products.size()];
        int c = 0;
        while(i.hasNext()){
            p[c] = i.next();
            c++;
        }
        return p;
    }

    private ProductData findProduct(String barcode){
        Iterator<ProductData> i = products.iterator();
        while(i.hasNext()){
            ProductData p = i.next();
            if(p.getBarcode().contentEquals(barcode))
                return p;
        }
        return null;
    }

    private ItemData[] getItems(){
        Iterator<ItemData> i = items.iterator();
        ItemData[] p = new ItemData[items.size()];
        int c = 0;
        while(i.hasNext()){
            p[c] = i.next();
            c++;
        }
        return p;
    }

    private class ScannerTimer extends TimerTask {

        @Override
        public void run() {
            if(!getView().getBarcode().isEmpty())
                addItem();
            timer.cancel();
        }
    }
}

