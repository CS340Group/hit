package gui.batches;

import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import common.command.CommandManager;
import common.command.TransferItemCommand;

/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements
		ITransferItemBatchController {

    ProductContainerData target;
    boolean scanner;
    Timer timer;

	private CommandManager _commandManager;
    private Hashtable<String, ArrayList<ItemData>> _transferredItems;
    private ProductVault _productVault = ProductVault.getInstance();
    ProductData _currentProduct = null;

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
        _commandManager = new CommandManager();
        _transferredItems = new Hashtable<String, ArrayList<ItemData>>();
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
        getView().setBarcode("");

        ArrayList<ProductData> products = new ArrayList<ProductData>();
        for (String barcode : _transferredItems.keySet()){
            Product p = _productVault.find("BarcodeString = %o",  barcode);
            if (p == null) {
                continue;   
            }
            ProductData pd = GuiModelConverter.wrapProduct(p);
            pd.setCount(Integer.toString(_transferredItems.get(barcode).size()));
            if (Integer.parseInt(pd.getCount()) > 0)
                products.add(pd);
        }
        this.getView().setProducts(products.toArray(new ProductData[0]));
        if(_currentProduct != null){
            this.getView().setItems(getRemovedItemsAsList(_currentProduct));
        }
	}

    /**
     * This is a helper function to avoid long lines:
     */
    private ArrayList<ItemData> getRemovedItemsForProduct(ProductData p){
        return _transferredItems.get(p.getBarcode());
    }

    private ItemData[] getRemovedItemsAsList(ProductData p){
        return getRemovedItemsForProduct(p).toArray(new ItemData[0]);
    }

    public void addItemToView(Item i){
        ItemData iData = GuiModelConverter.wrapItem(i);
        Product p = i.getProduct();
        if (_transferredItems.containsKey(p.getBarcodeString())){
            _transferredItems.get(p.getBarcodeString()).add(iData);
        } else {
            ArrayList<ItemData> its = new ArrayList<ItemData>();
            its.add(iData);
            _transferredItems.put(p.getBarcodeString(), its);
        }
        this.loadValues();
    }

    public void removeItemFromView(Item item){
        Product p = item.getProduct();
        if (_transferredItems.containsKey(p.getBarcodeString())){
            ItemData itemToDelete = null;
            for(ItemData i : _transferredItems.get(p.getBarcodeString())){
                if(Integer.toString(item.getId()).equals(i.getTag().toString()))
                    itemToDelete = i;
            }
            if (itemToDelete != null)
                _transferredItems.get(p.getBarcodeString()).remove(itemToDelete);
        }
        this.loadValues();
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

        getView().enableRedo(_commandManager.canRedo());
        getView().enableUndo(_commandManager.canUndo());
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
        _currentProduct = this.getView().getSelectedProduct();
        this.loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Transfer Item" button
	 * in the transfer item batch view.
	 */
	@Override
	public void transferItem() {
        Item item = ItemVault.getInstance().find("BarcodeString = %o", getView().getBarcode());
        if(item == null){
            getView().displayErrorMessage("Item not found");
            return;
        }
        StorageUnit targetSG = StorageUnitVault.getInstance().find("Id = %o", target.getTag());
        TransferItemCommand command = new TransferItemCommand(targetSG, item, this);
        _commandManager.executeCommand(command);

        loadValues();
        enableComponents();
	}

	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void redo() {
        _commandManager.redo();
        enableComponents();
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void undo() {
        _commandManager.undo();
        enableComponents();
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
}

