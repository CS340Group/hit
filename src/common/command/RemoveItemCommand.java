/**
 * 
 */
package common.command;

import model.item.Item;
import common.Result;

/**
 * Remove the specified item from the system.
 * @author murphyra
 *
 */
public class RemoveItemCommand extends AbstractCommand {
	
	Item _item;
	
	/**
	 * Construct a command to remove a specified item from the system.
	 * @param i the Item to b removed.
	 */
	public RemoveItemCommand(Item i) {
		_item = i;
	}

	@Override
	protected Result executeGuts() {
		_item.obliterate();
		return new Result(true);
	}

	@Override
	protected Result undoGuts() {
		_item.save();
		return new Result(true);
	}

}
