package gui.common;

public abstract class Vault implements IVault {

	/**
	* should never get a new object
	*/
	private Vault(){
		return null;
	}

	protected Model find(){
		assert false
		//THIS CLASS SHOULD BE OVERIDDEN
		return null;
	}

	protected static Result validateNew(Model model){
		assert false
		//THIS CLASS SHOULD BE OVERIDDEN
		return null;
	}

	protected static Result validateModified(Model model){
		assert false
		//THIS CLASS SHOULD BE OVERIDDEN
		return null;
	}

	protected static Result saveNew(Model model){
		assert false
		//THIS CLASS SHOULD BE OVERIDDEN
		return null;
	}

	protected static Result saveModified(Model model){
		assert false
		//THIS CLASS SHOULD BE OVERIDDEN
		return null;
	}
}