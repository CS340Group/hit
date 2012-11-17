package model.productidentifier;

/**
 * An interface for an object to iterate over a collection of PluginDescriptor classes.
 */
public interface IPluginDescriptorIterator {
	
	/**
	 * @return the next plugin descriptor in line, or null.
	 */
	public PluginDescriptor next();

}
