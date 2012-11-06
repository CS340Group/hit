/**
 * 
 */
package common.command;

import gui.batches.RemoveItemBatchController;
import model.item.Item;
import common.Result;

/**
 * Remove the specified item from the system.
 * @author murphyra
 *
 */
public class RemoveItemCommand extends AbstractCommand {
	
	Item _item;
	private RemoveItemBatchController _controller;
	
	/**
	 * Construct a command to remove a specified item from the system.
	 * @param i the Item to b removed.
	 */
	public RemoveItemCommand(Item item) {
		_item = item;
	}

	public RemoveItemCommand(Item item,
			RemoveItemBatchController removeItemBatchController) {
		this(item);
		_controller = removeItemBatchController;
	}

	@Override
	protected Result executeGuts() {
		_item.delete();
		return new Result(true);
	}

	@Override
	protected Result undoGuts() {
		_item.save();
		return new Result(true);
	}

}
