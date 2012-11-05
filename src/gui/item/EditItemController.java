package gui.item;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import model.item.Item;
import model.item.ItemVault;
import gui.common.*;

/**
 * Controller class for the edit item view.
 */
public class EditItemController extends Controller 
										implements IEditItemController {
	

	ItemData _itemData;
	Item _realItem;
	ItemVault _iVault;
	Date _minDate;

	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit item view
	 * @param target Item that is being edited
	 */
	public EditItemController(IView view, ItemData target) {
		super(view);

		_iVault = ItemVault.getInstance();
		_itemData = target;
		_realItem = _iVault.find("Id = %o",  _itemData.getTag());
		Calendar cal = Calendar.getInstance();
		cal.set(2000, 1, 1);
		_minDate = cal.getTime();
		construct();
		enableComponents();
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
	protected IEditItemView getView() {
		return (IEditItemView)super.getView();
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
		getView().enableBarcode(false);
		getView().enableDescription(false);

		Date entered = getView().getEntryDate();
		/* Cop out early if we can. */
		if (entered == null)
			return;
		
		checkValidityOfEnteredDate(entered);
	}

	private void checkValidityOfEnteredDate(Date entered) {
		_realItem.setEntryDate(new DateTime(entered));
		if (!_realItem.validate().getStatus()){
			getView().enableOK(false);
		}else{
			getView().enableOK(true);
		}
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
		getView().setDescription(_realItem.getProductDescription());
		getView().setBarcode(_itemData.getBarcode());
		getView().setEntryDate(_itemData.getEntryDate());
	}

	//
	// IEditItemController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit item view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit item view.
	 */
	@Override
	public void editItem() {
		Date newDate = getView().getEntryDate();
		_realItem.setEntryDate(new DateTime(newDate));
		common.Result result = _realItem.validate();
		result = _realItem.save();
	}

}

