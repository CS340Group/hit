/**
 *
 */
package model.productidentifier;

import common.Result;

import java.util.ArrayList;

/**
 * Keeps track of what plugins the program has loaded.
 */
public class ProductIdentificationPluginRegistry {

    private static ArrayList<PluginDescriptor> descriptors = new ArrayList<PluginDescriptor>();

    /**
     * Adds a new plugin to the system registry.
     *
     * @param d the descriptor of the plugin to add.
     * @return a result representing the success of the addition.
     */
    public static Result RegisterPlugin(PluginDescriptor d) {
        //Try to load the plugin.
        try {
            Class plugin = Class.forName(d.getClassName());
        } catch (ClassNotFoundException e) {
            return new Result(false, "Class not found.");
        }
        descriptors.add(d);
        return new Result(true, "Class successfully loaded.");
    }

    /**
     * @return An iterator for all of the plugin descriptors that can be used right now.
     */
    public static PluginDescriptorIterator getAvailablePlugins() {
        return new PluginDescriptorIterator(descriptors.iterator());
    }

}
