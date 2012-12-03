package gui.storageunit;

import gui.common.Controller;
import gui.common.IView;
import model.productcontainer.StorageUnit;

/**
 * Controller class for the add storage unit view.
 */
public class AddStorageUnitController extends Controller implements
        IAddStorageUnitController {

    /**
     * Constructor.
     *
     * @param view Reference to add storage unit view
     */
    public AddStorageUnitController(IView view) {
        super(view);

        construct();
    }

    //
    // Controller overrides
    //

    /**
     * Returns a reference to the view for this controller.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post Returns a reference to the view for this controller.}
     */
    @Override
    protected IAddStorageUnitView getView() {
        return (IAddStorageUnitView) super.getView();
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
        StorageUnit unit = new StorageUnit();
        unit.setName(getView().getStorageUnitName());
        getView().enableOK(unit.validate().getStatus());
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
        //NOT SURE WHAT TO DO HERE?
    }

    //
    // IAddStorageUnitController overrides
    //

    /**
     * This method is called when any of the fields in the
     * add storage unit view is changed by the user.
     */
    @Override
    public void valuesChanged() {
        enableComponents();
    }

    /**
     * This method is called when the user clicks the "OK"
     * button in the add storage unit view.
     */
    @Override
    public void addStorageUnit() {
        StorageUnit unit = new StorageUnit();
        unit.setName(getView().getStorageUnitName());
        unit.validate().getStatus();
        unit.save().getStatus();
    }

}

