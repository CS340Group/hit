package common.command;

import com.sun.tools.hat.internal.model.ReachableExcludesImpl;

import common.Result;

import model.common.ModelFacade;
import model.item.Item;
import model.productcontainer.StorageUnit;

/**
 * Adds the specified item to the specified storage unit.
 * @author murphyra
 */
public class AddItemCommand extends AbstractCommand {

	ModelFacade _modelFacade;
	Item _item;
	StorageUnit _targetSU;
	
	/**
	 * Construct a new command from an item and a storage unit.
	 * Item will be added to the system. When undo is called, the item will be removed from the system.
	 * @param i the item to be added. The item must be valid.
	 * @param su the storage unit to which the item should be added.
	 */
	public AddItemCommand(Item i, StorageUnit su){
		_modelFacade = new ModelFacade();
		_item = i;
		_targetSU = su;
	}
	
	@Override
	protected Result executeGuts() {
		if (!_item.isValid() || !_targetSU.isValid())
			return new Result(false, "Item and Storage Unit must be valid.");
		
		return _modelFacade.AddItem(_targetSU, _item);
	}

	@Override
	protected Result undoGuts() {
		return _modelFacade.RemoveItem(_item);
	}

}
