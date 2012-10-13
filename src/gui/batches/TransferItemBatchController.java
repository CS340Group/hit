package gui.batches;

import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements
		ITransferItemBatchController {

    ProductContainerData target;
    boolean scanner;
    Timer timer;

    ArrayList<ProductData> productDatas;
    ArrayList<ItemData> itemDatas;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the transfer item batch view.
	 * @param target Reference to the storage unit to which items are being transferred.
	 */
	public TransferItemBatchController(IView view, ProductContainerData target) {
		super(view);
        this.target = target;
        getView().setUseScanner(scanner = true);
        this.timer = new Timer();
        this.productDatas = new ArrayList<ProductData>();
        this.itemDatas = new ArrayList<ItemData>();
        construct();

    }
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected ITransferItemBatchView getView() {
		return (ITransferItemBatchView)super.getView();
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
        getView().enableItemAction(
                !getView().getBarcode().isEmpty()
                 && !scanner
        );
	}

	/**
	 * This method is called when the "Item Barcode" field in the
	 * transfer item batch view is changed by the user.
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
	 * transfer item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
        scanner = getView().getUseScanner();
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the transfer item batch view.
	 */
	@Override
	public void selectedProductChanged() {
	}
	
	/**
	 * This method is called when the user clicks the "Transfer Item" button
	 * in the transfer item batch view.
	 */
	@Override
	public void transferItem() {
        Item item = ItemVault.getInstance().find("Barcode = "+getView().getBarcode());
        if(item == null){
            getView().displayErrorMessage("Item not found");
            return;
        }

        Product product = item.getProduct();

        ArrayList<Product> products = ProductVault.getInstance().findAll("Barcode = " + item.getProduct().getBarcode());

        boolean flag = true;
        for(Product p : products){
            if(p.getStorageUnitId() == (Integer) target.getTag())
                flag = false;
        }

        if(flag){
            product = new Product(item.getProduct());
            product.setStorageUnitId((Integer) target.getTag());
            product.validate();
            product.save();
        }

        item.setProductId(product.getId());
        item.validate();
        item.save();

        ProductData pd = findProduct(getView().getBarcode());
        if(pd == null){
            pd = new ProductData();
            pd.setCount("0");
            pd.setSize(product.getSize().toString());
            pd.setSupply(String.valueOf(product.get3MonthSupply()));
            pd.setBarcode(product.getBarcode().toString());
            pd.setDescription(product.getDescription());
            pd.setShelfLife(String.valueOf(product.getShelfLife()));
            pd.setTag(product.getId());
        }

        ItemData id = new ItemData();
        id.setBarcode(item.getBarcode().toString());
        id.setEntryDate(item.getEntryDate().toDate());
        id.setExpirationDate(item.getExpirationDate().toDate());
        try{id.setProductGroup(product.getContainer().getName());}
        catch(NullPointerException e){ id.setProductGroup(""); }
        id.setStorageUnit(product.getStorageUnit().getName());
        id.setTag(item.getId());
        itemDatas.add(id);

        int count = Integer.parseInt(pd.getCount())+1;
        pd.setCount(String.valueOf(count));

        productDatas.add(pd);

        loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the transfer item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}

    private class ScannerTimer extends TimerTask {

        @Override
        public void run() {
            if(!getView().getBarcode().isEmpty())
                transferItem();
            timer.cancel();
        }
    }

    private ProductData[] getProducts(){
        Iterator<ProductData> i = productDatas.iterator();
        ProductData[] p = new ProductData[productDatas.size()];
        int c = 0;
        while(i.hasNext()){
            p[c] = i.next();
            c++;
        }
        return p;
    }

    private ProductData findProduct(String barcode){
        Iterator<ProductData> i = productDatas.iterator();
        while(i.hasNext()){
            ProductData p = i.next();
            if(p.getBarcode().contentEquals(barcode))
                return p;
        }
        return null;
    }

    private ItemData[] getItems(){
        Iterator<ItemData> i = itemDatas.iterator();
        ItemData[] p = new ItemData[itemDatas.size()];
        int c = 0;
        while(i.hasNext()){
            p[c] = i.next();
            c++;
        }
        return p;
    }
}

