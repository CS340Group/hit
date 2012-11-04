package model.productcontainer;

import java.util.ArrayList;
import model.common.IModel;
import model.common.Vault;
import common.Result;
import model.productcontainer.StorageUnit;


/**
 * The Product class provides a way to query for ProductModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Product.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class StorageUnitVault extends Vault{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static StorageUnitVault currentInstance;
	
	

	/**
	 * Returns a reference to the only instance allowed for this class.
	 */
	public static synchronized StorageUnitVault getInstance(){
		if(currentInstance == null) currentInstance = new StorageUnitVault();
		return currentInstance;
	}

    
	/**
	 * Returns just one StorageUnit based on the query sent in. 
	 * If you need more than one StorageUnit returned use FindAll
	 * 
	 * @param attribute Which attribute should we search on for each Product
	 * @param value What value does the column have
	 * 
	 */
	public  StorageUnit find(String query, Object... params)  {
		return (StorageUnit)findPrivateCall(query);
	}
	
	
	/**
	 * Returns a list of StorageUnits which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public  ArrayList<StorageUnit> findAll(String query, Object... params) {
		return (ArrayList)this.findAllPrivateCall(query);
	}
	public int getLastIndex(){
		return (int)dataVault.size()+ProductGroupVault.getInstance().size();
	}
	public StorageUnit get(int id){
		return (StorageUnit) this.getPrivateCall(id);
	}
	protected StorageUnit getNewObject(){
		return new StorageUnit();
	}
	protected StorageUnit getCopiedObject(IModel model){
		return new StorageUnit((StorageUnit)model);
	}
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * - Must have a unique name
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected  Result validateNew(IModel model){
		Result result = new Result();
		result = checkUniqueName((StorageUnit) model);
		if(result.getStatus() == false)
			return result;

        model.setValid(true);
		return result;
	}
	/**
	 * Adds the StorageUnit to the map if it's new.  Should check before doing so.
	 * 
	 * @param model StorageUnit to add
	 * @return Result of request
	 */
	protected  Result saveNew(StorageUnit model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if(dataVault.isEmpty() && model._productGroupVault.size() == 0 )
            id = 0;
        else
            id = dataVault.size() + 1 +model._productGroupVault.size();

        model.setId(id);
        model._rootParentId = id;
        model.setSaved(true);
        this.addModel(new StorageUnit(model));
        return new Result(true);
	}
	private  Result checkUniqueName(StorageUnit model){
        //Null check
        if(model.getName() == null)
            return new Result(false, "Name can't be null");

        if(model.getName() == "")
            return new Result(false, "Name can't be empty");

		int size = findAll("Name = "+model.getName()).size();
		if(size!=0)
			return new Result(false,"Duplicate storage container name.");
		return new Result(true);
	}
	/**
	 * Private constructor, for the singleton design pattern.
	 */
	private StorageUnitVault(){
		currentInstance = this;
	}

	

	

}

