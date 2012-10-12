package gui.storageunit;

import common.Result;
import gui.common.*;
import gui.inventory.*;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;

/**
 * Controller class for the edit storage unit view.
 */
public class EditStorageUnitController extends Controller 
										implements IEditStorageUnitController {

    public ProductContainerData getTarget() {
        return target;
    }

    public void setTarget(ProductContainerData _target) {
        this.target = _target;
    }

    ProductContainerData target;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit storage unit view
	 * @param target The storage unit being edited
	 */
	public EditStorageUnitController(IView view, ProductContainerData target) {
		super(view);

		construct();
        this.target = target;
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
	protected IEditStorageUnitView getView() {
		return (IEditStorageUnitView)super.getView();
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
        if(target == null)
            return;

        int id = ((Number)getTarget().getTag()).intValue();
        StorageUnit su = StorageUnitVault.getInstance().get(id);
        su.setName(getView().getStorageUnitName());
        getView().enableOK(su.validate().getStatus());
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
	// IEditStorageUnitController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit storage unit view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
        enableComponents();
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit storage unit view.
	 */
	@Override
	public void editStorageUnit() {
        int id = ((Number)getTarget().getTag()).intValue();
        StorageUnit su = StorageUnitVault.getInstance().get(id);
        su.setName(getView().getStorageUnitName());
        su.validate();
        su.save();
	}

}

