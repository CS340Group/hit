/**
 * 
 */
package model.productidentifier;

import model.product.Product;

/**
 * Defines an interface for a plugin which searches the web to identify a product by its barcode.
 */
public interface IProductIdentificationPlugin extends
		IProductIdentificationHandler {
	
	/**
	 * @param barcode a string representing the barcode of the product to be identified.
	 * @return a Product instance with the located information, or null.
	 */
	public Product identify(String barcode);

}
