package gui.productgroup;

import gui.common.Controller;
import gui.common.IView;
import gui.common.SizeUnits;
import gui.inventory.ProductContainerData;
import model.common.Size;
import model.productcontainer.ProductContainer;
import model.productcontainer.ProductGroup;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnitVault;

/**
 * Controller class for the add product group view.
 */
public class AddProductGroupController extends Controller implements
        IAddProductGroupController {

    private ProductContainerData containerData;

    /**
     * Constructor.
     *
     * @param view      Reference to add product group view
     * @param container Product container to which the new product group is being added
     */
    public AddProductGroupController(IView view, ProductContainerData container) {
        super(view);
        setContainerData(container);
        getView().setSupplyUnit(SizeUnits.Count);
        getView().setSupplyValue("0");
        construct();
    }

    public ProductContainerData getContainerData() {
        return containerData;
    }

    public void setContainerData(ProductContainerData containerData) {
        this.containerData = containerData;
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
    protected IAddProductGroupView getView() {
        return (IAddProductGroupView) super.getView();
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
        ProductContainer unit = StorageUnitVault.getInstance()
                .get(((Number) getContainerData().getTag()).intValue());
        if (unit == null)
            unit = ProductGroupVault.getInstance()
                    .get(((Number) getContainerData().getTag()).intValue());
        ProductGroup pg = new ProductGroup();
        pg.setRootParentId(unit.getRootParentId());
        pg.setParentId(unit.getId());
        float size = 0;
        try {
            size = Float.parseFloat(getView().getSupplyValue());
        } catch (Exception e) {
            getView().enableOK(false);
            return;
        }
        if (!pg.set3MonthSupply(new Size(size, getView().getSupplyUnit().toString())).getStatus()) {
            getView().enableOK(false);
            return;
        }
        pg.setName(getView().getProductGroupName());
        getView().enableOK(pg.validate().getStatus());
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
    }

    //
    // IAddProductGroupController overrides
    //

    /**
     * This method is called when any of the fields in the
     * add product group view is changed by the user.
     */
    @Override
    public void valuesChanged() {
        enableComponents();
    }

    /**
     * This method is called when the user clicks the "OK"
     * button in the add product group view.
     */
    @Override
    public void addProductGroup() {
        ProductContainer unit = StorageUnitVault.getInstance()
                .get(((Number) getContainerData().getTag()).intValue());
        if (unit == null)
            unit = ProductGroupVault.getInstance()
                    .get(((Number) getContainerData().getTag()).intValue());
        ProductGroup pg = new ProductGroup();
        pg.setRootParentId(unit.getId());
        pg.setParentId(unit.getId());
        float size = Float.parseFloat(getView().getSupplyValue());
        Size tempSize = new Size(size, getView().getSupplyUnit().toString());
        pg.set3MonthSupply(tempSize);
        pg.setName(getView().getProductGroupName());
        pg.validate();
        pg.save();
    }

}

