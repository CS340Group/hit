/**
 *
 */
package model.productidentifier;

import java.lang.reflect.InvocationTargetException;

/**
 * @author murphyra
 *         The product identifier class accepts a barcode string,
 *         and loads all plugins for product identification
 *         from the plugin registry. Then, using the chain of
 *         responsibility design pattern, all plugins work
 *         together to identify the product. If one does not find it,
 *         it passes the message to the next until either
 *         one plugin finds the product, or none do. In the latter case,
 *         a null pointer will be returned.
 */
public class ProductIdentifier {

    private static ProductIdentificationPlugin plugins = null;

    /**
     * @param barcode A string containing the product's barcode.
     * @return a copy of a product with the description, etc... filled out.
     */
    public static String identify(String barcode) {
        return getPlugins().identify(barcode);
    }

    private static ProductIdentificationPlugin getPlugins() {
        if (plugins != null)
            return plugins;

        PluginDescriptorIterator i = ProductIdentificationPluginRegistry.getAvailablePlugins();
        while (i.hasNext()) {
            try {
                plugins = i.next().getConstructor(ProductIdentificationPlugin.class)
                        .newInstance(plugins);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return plugins;
    }

}
