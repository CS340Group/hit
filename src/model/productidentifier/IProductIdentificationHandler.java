/**
 * 
 */
package model.productidentifier;

import model.product.Product;

/**
 * Defines an interface for handling the action of identifying a product from the web.
 */
public interface IProductIdentificationHandler {
	
	/**
	 * The class that implements this interface will attempt to handle the identification 
	 * request on its own, but will pass the request along to the next plugin if no result is found.
	 * @param barcode a string representing the barcode of the product to be identified.
	 * @return a Product instance with the located information, or null.
	 */
	public Product handle(String barcode);

}
