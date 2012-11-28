/**
 * 
 */
package model.productidentifier;

import model.product.Product;

/**
 * Attempts to identify a product by searching Google.
 */
public class ProductIdentifierGoogle extends ProductIdentificationPlugin {

    public ProductIdentifierGoogle(ProductIdentificationPlugin successor) {
        super(successor);
    }


	/* (non-Javadoc)
	 * @see model.productidentifier.ProductIdentificationPlugin#identify(java.lang.String)
	 */
	@Override
	public String identify(String barcode) {
        System.out.println("GOOGLE PLUGIN");
		return handoff(barcode);
	}

}
