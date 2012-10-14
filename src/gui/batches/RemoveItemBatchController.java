package gui.batches;

import java.util.ArrayList;
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

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
		IRemoveItemBatchController {
	
	private ProductVault _productVault;
	private ItemVault _itemVault;
	private ItemData currentItem;
	private ArrayList<ProductData> _removedProducts;
	private Map<ProductData, ArrayList<ItemData>> _removedItems;
	
    boolean scanner = true;
    Timer timer;
    ModelFacade _baseModel;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the remove item batch view.
	 */
	public RemoveItemBatchController(IView view) {
		super(view);
		_productVault = ProductVault.getInstance();
		_itemVault = ItemVault.getInstance();
		timer = new Timer();
		_baseModel = new ModelFacade();
		construct();
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
//		this.getView().setProducts(this._removedProducts.toArray());
//		this.getView().setItems(this.getItems());
//		this.getView().setBarcode("");
	}
	
	private ItemData[] getItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		items = _itemVault.findAll("Deleted = false");
		return GuiModelConverter.wrapItems(items);
	}
	
	private ProductData[] getProducts() {
		ArrayList<Product> products = new ArrayList<Product>();
		products = _productVault.findAll("Deleted = false");
		return GuiModelConverter.wrapProducts(products);
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
	}

	/**
	 * This method is called when the "Item Barcode" field is changed
	 * in the remove item batch view by the user.
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
	 * This method is called when the "Use Barcode Scanner" setting is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void useScannerChanged() {
        scanner = getView().getUseScanner();
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the remove item batch view.
	 */
	@Override
	public void selectedProductChanged() {
	}
	
	/**
	 * This method is called when the user clicks the "Remove Item" button
	 * in the remove item batch view.
	 */
	@Override
	public void removeItem() {
		String bcs = this.getView().getBarcode();
		Item item = ItemVault.getInstance().find("BarcodeString = " + bcs);
		common.Result r = _baseModel.RemoveItem(item);
		if (!r.getStatus()) {
			this.getView().displayInformationMessage("There is no item with that barcode.");
		}else{
			Product p = ProductVault.getInstance().get(item.getProductId());
			ProductData pData = GuiModelConverter.wrapProduct(p);
			_removedProducts.add(pData);
		}
		this.loadValues();
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
            timer.cancel();
        }
    }

}

