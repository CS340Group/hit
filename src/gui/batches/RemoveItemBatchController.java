package gui.batches;

import common.command.CommandManager;
import common.command.RemoveItemCommand;
import gui.common.Controller;
import gui.common.GuiModelConverter;
import gui.common.IView;
import gui.item.ItemData;
import gui.product.ProductData;
import model.common.ModelFacade;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
        IRemoveItemBatchController {

    private ProductVault _productVault;
    private Hashtable<String, ArrayList<ItemData>> _removedItems;

    boolean _scanner = true;
    Timer _timer;
    ModelFacade _ModelFacade;
    ProductData _currentProduct;
    private CommandManager _commandManager = new CommandManager();

    /**
     * Constructor.
     *
     * @param view Reference to the remove item batch view.
     */
    public RemoveItemBatchController(IView view) {
        super(view);
        _productVault = ProductVault.getInstance();
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
        return (IRemoveItemBatchView) super.getView();
    }

    /**
     * Loads data into the controller's view.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post The controller has loaded data into its view}
     */
    @Override
    protected void loadValues() {
        ArrayList<ProductData> products = new ArrayList<ProductData>();
        for (String barcode : _removedItems.keySet()) {
            Product p = _productVault.find("BarcodeString = %o", barcode);
            if (p == null) {
                continue;
            }
            ProductData pd = GuiModelConverter.wrapProduct(p);
            pd.setCount(Integer.toString(_removedItems.get(barcode).size()));
            if (Integer.parseInt(pd.getCount()) > 0)
                products.add(pd);
        }
        this.getView().setProducts(products.toArray(new ProductData[0]));
        if (_currentProduct != null) {
            this.getView().setItems(getRemovedItemsAsList(_currentProduct));
        }
    }

    /**
     * This is a helper function to avoid long lines:
     */
    private ArrayList<ItemData> getRemovedItemsForProduct(ProductData p) {
        return _removedItems.get(p.getBarcode());
    }

    private ItemData[] getRemovedItemsAsList(ProductData p) {
        return getRemovedItemsForProduct(p).toArray(new ItemData[0]);
    }

    /**
     * Sets the enable/disable state of all components in the controller's view.
     * A component should be enabled only if the user is currently
     * allowed to interact with that component.
     * <p/>
     * {@pre None}
     * <p/>
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

        getView().enableRedo(_commandManager.canRedo());
        getView().enableUndo(_commandManager.canUndo());
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
        } catch (IllegalStateException e) {
        }
        if (_scanner)
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
        Item item = ItemVault.getInstance().find("BarcodeString = %o", bcs);
        if (item == null) {
            this.getView().displayErrorMessage("There is no item with that barcode.");
        } else {
            RemoveItemCommand command = new RemoveItemCommand(item, this);
            _commandManager.executeCommand(command);
        }
        this.loadValues();
        getView().setBarcode("");
        getView().giveBarcodeFocus();
        this.enableComponents();
    }

    public void addItemToView(Item i) {
        ItemData iData = GuiModelConverter.wrapItem(i);
        Product p = i.getProduct();
        if (_removedItems.containsKey(p.getBarcodeString())) {
            _removedItems.get(p.getBarcodeString()).add(iData);
        } else {
            ArrayList<ItemData> its = new ArrayList<ItemData>();
            its.add(iData);
            _removedItems.put(p.getBarcodeString(), its);
        }
        this.loadValues();
    }

    public void removeItemFromView(Item item) {
        Product p = item.getProduct();
        if (_removedItems.containsKey(p.getBarcodeString())) {
            ItemData itemToDelete = null;
            for (ItemData i : _removedItems.get(p.getBarcodeString())) {
                if (Integer.toString(item.getId()).equals(i.getTag().toString()))
                    itemToDelete = i;
            }
            if (itemToDelete != null)
                _removedItems.get(p.getBarcodeString()).remove(itemToDelete);
        }
        this.loadValues();
    }

    /**
     * This method is called when the user clicks the "Redo" button
     * in the remove item batch view.
     */
    @Override
    public void redo() {
        _commandManager.redo();
        enableComponents();
    }

    /**
     * This method is called when the user clicks the "Undo" button
     * in the remove item batch view.
     */
    @Override
    public void undo() {
        _commandManager.undo();
        enableComponents();
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
            if (!getView().getBarcode().isEmpty())
                removeItem();
            _timer.cancel();
        }
    }

}

