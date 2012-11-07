/**
 * 
 */
package common.command;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.Barcode;


import ch.lambdaj.Lambda.*;

import model.item.Item;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.StorageUnit;
import gui.batches.ITransferItemBatchController;
import gui.inventory.ProductContainerData;
import common.Result;

/**
 * A commmand which moves an item to a different ProductCointainer.
 * @author murphyra
 */
public class TransferItemCommand extends AbstractCommand {
	
	private Item _item;
	private StorageUnit _targetSU;
	private ProductVault _productVault = ProductVault.getInstance();
	private Product _oldProduct;
	private Product _newProduct;
	private boolean _createdProduct = false;
	private ITransferItemBatchController _controller;

	/**
	 * Moves an item from its current product container to the target product container.
	 * @param pc The product container to move the item into.
	 * @param i The item to move.
	 */
	public TransferItemCommand(StorageUnit targetSU, Item item){
		assert item != null;
		assert targetSU != null;
		_targetSU = targetSU;
		_item = item;
		_oldProduct = item.getProduct();
		_newProduct = null;
	}

	public TransferItemCommand(StorageUnit target, Item item,
			ITransferItemBatchController transferItemBatchController) {
		this(target, item);
		_controller = transferItemBatchController;
	}

	@Override
	protected Result executeGuts() {
		// Search for a product in the target storage unit.
		ArrayList<Product> psFromTargetUnit = 
							_productVault.findAll("StorageUnitId = %o", _targetSU.getId());
		for(Product product : psFromTargetUnit){
			if (product.getBarcode().equals(_oldProduct.getBarcode()))
				_newProduct = product;
				_newProduct.save();
		}
		
		// If we didn't find it, make a copy of the old one.
		if(_newProduct == null){
			_newProduct = new Product(_oldProduct);
			_newProduct.setId(-1);
			_newProduct.setStorageUnitId(_targetSU.getId());
			_newProduct.setContainerId(_targetSU.getId());
			_newProduct.save();
			_createdProduct = true;
		}
		
		// Move the item over
		_item.setProduct(_newProduct);

		// Update the view if we have a view.
		if (_controller != null)
			_controller.addItemToView(_item);

		Result result = _item.save();
		return result;
	}

	@Override
	protected Result undoGuts() {
		_item.setProduct(_oldProduct);
		if(_createdProduct)
			_newProduct.obliterate();
		_newProduct = null;
		_createdProduct = false;

		// Update the view if we have a view.
		if (_controller != null)
			_controller.removeItemFromView(_item);
		
		return _item.save();
	}

}
