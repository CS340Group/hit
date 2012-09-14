package gui.common;

/**
 * Vault objects are responsible for data retrieval and persistance
 */
public interface Vault {

	

	/**
	 * If the Model has been validated then it must be saved.
	 * This should always return a good result as you should validate before you save.
	 *
	 */
	Result save();

}

