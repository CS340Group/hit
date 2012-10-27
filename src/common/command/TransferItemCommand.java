/**
 * 
 */
package common.command;

import model.item.Item;
import model.productcontainer.ProductContainer;
import gui.inventory.ProductContainerData;
import common.Result;

/**
 * A commmand which moves an item to a different ProductCointainer.
 * @author murphyra
 */
public class TransferItemCommand extends AbstractCommand {
	
	/**
	 * Moves an item from its current product container to the target product container.
	 * @param pc The product container to move the item into.
	 * @param i The item to move.
	 */
	public TransferItemCommand(ProductContainer pc, Item i){
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
