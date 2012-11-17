/**
 * 
 */
package model.productidentifier;

import common.Result;

/**
 * Keeps track of what plugins the program has loaded.
 */
public class ProductIdentificationPluginRegistry {
	
	/**
	 * Adds a new plugin to the system registry.
	 * @param d the descriptor of the plugin to add.
	 * @return a result representing the success of the addition.
	 */
	public Result RegisterPlugin(PluginDescriptor d){
		return null;
	}
	
	/**
	 * @return An iterator for all of the plugin descriptors that can be used right now.
	 */
	public IPluginDescriptorIterator getAvailablePlugins(){
		return null;
	}

}
