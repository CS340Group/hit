package gui.batches;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

import model.common.ModelFacade;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;

import gui.common.*;
import gui.item.ItemData;
import gui.product.*;

/* TODO:
 * Main view needs to update when a product is removed from this view.
 * Enable / Disable needs to work.
 * Product needs to show the count of its items that have been removed.
 * The changes to the actual data should not take effect until the done button is pressed.
 * Make sure keyboard focus works properly.
 */

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
		IRemoveItemBatchController {
	
	private ProductVault _productVault;
	private ItemVault _itemVault;
	private Hashtable<String, ArrayList<ItemData>> _removedItems;
	
    boolean _scanner = true;
    Timer _timer;
    ModelFacade _ModelFacade;
    ProductData _currentProduct;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the remove item batch view.
	 */
	public RemoveItemBatchController(IView view) {
		super(view);
		_productVault = ProductVault.getInstance();
		_itemVault = ItemVault.getInstance();
		_timer = new Timer();
		_ModelFacade = new ModelFacade();
		_removedItems = new Hashtable<String, ArrayList<ItemData>>();
		construct();

		// Give the barcode field focus:
		this.getView().giveBarcodeFocus();
		this.getView().setUseScanner(true);

		enableComponents();
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IRemoveItemBatchView getView() {
		return (IRemoveItemBatchView)super.getView();
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
		ArrayList<ProductData> products = new ArrayList<ProductData>();
		for (String barcode : _removedItems.keySet()){
			Product p = _productVault.find("BarcodeString = " + barcode);
			if (p == null) {
				continue;	
			}
			ProductData pd = GuiModelConverter.wrapProduct(p);
			pd.setCount(Integer.toString(_removedItems.get(barcode).size()));
			products.add(pd);
		}
		this.getView().setProducts(GuiModelConverter.productDataListToArray(products));
		if(_currentProduct != null){
			this.getView().setItems(GuiModelConverter.itemDataListToArray(_removedItems.get(_currentProduct.getBarcode())));
		}
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
		if (getView().getUseScanner() || getView().getBarcode().isEmpty()) {
			getView().enableItemAction(false);
		} else {
			getView().enableItemAction(true);
		}
	}

	/**
	 * This method is called when the "Item Barcode" field is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void barcodeChanged() {
        try {
            _timer.cancel();
            _timer = new Timer();
        }
        catch(IllegalStateException e){ }
        if(_scanner)
            _timer.schedule(new ScannerTimer(), SCANNER_SECONDS);
        enableComponents();
	}
	
	/**
	 * This method is called when the "Use Barcode Scanner" setting is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void useScannerChanged() {
        _scanner = getView().getUseScanner();
        enableComponents();
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the remove item batch view.
	 */
	@Override
	public void selectedProductChanged() {
		_currentProduct = this.getView().getSelectedProduct();
		this.loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Remove Item" button
	 * in the remove item batch view.
	 */
	@Override
	public void removeItem() {
		String bcs = this.getView().getBarcode();
		Item item = ItemVault.getInstance().find("BarcodeString = " + bcs);
		if (item == null) {
			this.getView().displayErrorMessage("There is no item with that barcode.");
		}else{
			_ModelFacade.RemoveItem(item);
			Product product = ProductVault.getInstance().get(item.getProductId());
			recordRemoved(item, product);
		}
		this.loadValues();
		getView().giveBarcodeFocus();
	}

	private void recordRemoved(Item i, Product p){
		ItemData iData = GuiModelConverter.wrapItem(i);
		if (_removedItems.containsKey(p.getBarcodeString())){
			_removedItems.get(p.getBarcodeString()).add(iData);
		} else {
			ArrayList<ItemData> its = new ArrayList<ItemData>();
			its.add(iData);
			_removedItems.put(p.getBarcodeString(), its);
		}
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the remove item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the remove item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the remove item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}
	
    private class ScannerTimer extends TimerTask {

        @Override
        public void run() {
            if(!getView().getBarcode().isEmpty())
                removeItem();
            _timer.cancel();
        }
    }

}

