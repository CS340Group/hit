/**
 * 
 */
package model.productidentifier;

import model.product.Product;

/**
 * @author murphyra
 * The product identifier class accepts a barcode string, and loads all plugins for product identification
 * from the plugin registry. Then, using the chain of responsibility design pattern, all plugins work 
 * together to identify the product. If one does not find it, it passes the message to the next until either
 * one plugin finds the product, or none do. In the latter case, a null pointer will be returned.
 */
public class ProductIdentifier {
	
	/**
	 * @param barcode A string containing the product's barcode.
	 * @return a copy of a product with the description, etc... filled out.
	 */
	public Product identify(String barcode){
		return null;
	}

}
