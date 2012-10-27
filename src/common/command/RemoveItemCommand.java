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
	
	/**
	 * Construct a command to remove a specified item from the system.
	 * @param i the Item to b removed.
	 */
	public RemoveItemCommand(Item i) {
		// TODO
	}

	@Override
	protected Result executeGuts() {
		// TODO Auto-generated method stub
		return new Result(false);
	}

	@Override
	protected Result undoGuts() {
		// TODO Auto-generated method stub
		return new Result(false);
	}

}
