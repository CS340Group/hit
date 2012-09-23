package model.common;

import java.util.Stack;

import common.Result;
/**
 * The history class keeps charge of any changes made to the vaults.  It has the ability to undo any changes.
 * <PRE>
 * Product.UndoAction() // Reverts the vault to how it was before
 * </PRE>
 * 
 */
public class History{
	Stack<Action> historyStack;
	
	public History(){
		return;
	}
	
	/**
	 * This should be called by passing the currentModel in the vault to the
	 * new model that is being created.  It will create the action and push it to the history
	 * 
	 * @param currentModel Model in Vault which is copied to the action
	 * @param newModel Model yet to be saved, this will point directly to it the vault
	 * @return
	 */
	public Result AddAction(IModel currentModel, IModel newModel){
		return null;
	}
	
	/**
	 * Pops the last action and makes sure to revert it back.
	 * It returns the vaults back to how they should be.
	 * 
	 * @return
	 */
	public Result UndoAction(){
		return null;
		
	}
}