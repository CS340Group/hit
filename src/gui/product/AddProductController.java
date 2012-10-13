package gui.product;

import gui.common.*;
import gui.inventory.ProductContainerData;
import model.common.Barcode;
import model.common.Size;
import model.product.Product;
import org.joda.time.DateTime;

/**
 * Controller class for the add item view.
 */
public class AddProductController extends Controller implements
		IAddProductController {

    private Barcode barcode;
    private ProductContainerData target;

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add product view
	 * @param barcode Barcode for the product being added
	 */
	public AddProductController(IView view, String barcode, ProductContainerData target) {
		super(view);
		this.barcode = new Barcode(barcode);
        this.target = target;
		construct();
        getView().setBarcode(this.barcode.toString());
        getView().enableBarcode(false);
        getView().setSizeValue("1");
        getView().enableSizeValue(false);
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

	//
	// Controller overrides
	//
	
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected IAddProductView getView() {
		return (IAddProductView)super.getView();
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
        getView().enableSizeValue(true);
        if(getView().getSizeUnit().name() == "count"){
            getView().enableSizeValue(false);
            getView().setSizeValue("1");
        }
        try{
            Float.parseFloat(getView().getSizeValue());
            Integer.parseInt(getView().getShelfLife());
            Integer.parseInt(getView().getSupply());
        } catch (Exception e){
            getView().enableOK(false);
        }
        getView().enableOK(!getView().getDescription().isEmpty());



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

	}

	//
	// IAddProductController overrides
	//
	
	/**
	 * This method is called when any of the fields in the
	 * add product view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
        enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product view.
	 */
	@Override
	public void addProduct() {
        Product product = new Product();
        product.set3MonthSupply(Integer.parseInt(getView().getSupply()));
        product.setBarcode(this.barcode);
        product.setContainerId((Integer) target.getTag());
        product.setCreationDate(DateTime.now());
        product.setDescription(getView().getDescription());
        product.setShelfLife(Integer.parseInt(getView().getShelfLife()));
        product.setSize(new Size(Float.parseFloat(getView().getSizeValue()),
                Size.Unit.values()[Math.abs(getView().getSizeUnit().ordinal()-9)]));
        product.setStorageUnitId((Integer) target.getTag());
        product.validate();
        product.save();
	}

}

