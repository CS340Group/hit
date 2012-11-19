/**
 * 
 */
package model.storage;

import model.common.IModel;

import common.Result;

/**
 * The interface for all data access objects. Provides an easy way to work with the 
 * storage of models.
 */
public interface IStorageDAO {
	
	/**
	 * Inserts a new model into storage.
	 * @param model An instance of a model.
	 * @return a result indicating the success of the insertion.
	 */
	public Result insert(IModel model);

	/**
	 * @param model An instance of a model subclass with the same id as the model to be updated in storage.
	 * @return a result indicating the success of the update.
	 */
	public Result update(IModel model);
	
	/**
	 * Deletes a model from storage.
	 * @param model An instance of a model subclass with the same id as the model to be deleted from storage.
	 * @return a result indicating the success of the deletion.
	 */
	public Result delete(IModel model);
	
	/**
	 * Takes an id of a model, searches the storage for the presence of the model,
	 * and returns the found object. If no object was found, returns null.
	 * @param id of the model to get. 
	 * @return an copy of the model.
	 */
	public IModel get(int id);

}
