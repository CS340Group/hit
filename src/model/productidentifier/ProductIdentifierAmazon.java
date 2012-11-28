/**
 * 
 */
package model.productidentifier;

import model.product.Product;

/**
 * Attempts to identify a product by searching Amazon.
 */
public class ProductIdentifierAmazon extends ProductIdentificationPlugin {

    public ProductIdentifierAmazon(ProductIdentificationPlugin successor){
        super(successor);
    }

    @Override
    public String identify(String barcode) {
        System.out.println("AMAZON PLUGIN");
        return handoff(barcode);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
