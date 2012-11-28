/**
 * 
 */
package model.productidentifier;

/**
 * Defines an interface for a plugin which searches the web to identify a product by its barcode.
 */
public abstract class ProductIdentificationPlugin {

    private ProductIdentificationPlugin successor;

    public ProductIdentificationPlugin(ProductIdentificationPlugin successor){
        this.successor = successor;
    }
	
	/**
	 * @param barcode a string representing the barcode of the product to be identified.
	 * @return a Product instance with the located information, or null.
	 */
	public abstract String identify(String barcode);


    protected String handoff(String barcode){
        if(successor == null)
            return "";
        else
            return successor.identify(barcode);

    }

}
