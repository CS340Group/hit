/**
 * 
 */
package model.productidentifier;

import model.product.Product;

/**
 * Searches UPCSearchDotOrg for the product in question.
 */
public class ProductIdentifierUPCSearchDotOrg extends
        ProductIdentificationPlugin {


    public ProductIdentifierUPCSearchDotOrg(ProductIdentificationPlugin successor) {
        super(successor);
    }

    @Override
    public String identify(String barcode) {
        System.out.println("UPCSEARCH PLUGIN");
        return handoff(barcode);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
