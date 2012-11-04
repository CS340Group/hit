package model.productcontainer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import model.common.IModel;
import model.common.Vault;
import model.productcontainer.ProductGroup;
import common.Result;


/**
 * The Product class provides a way to query for ProductModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Product.find(2) // returns ProductModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ProductGroupVault extends Vault {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ProductGroupVault currentInstance;
	
	/**
	 * Private constructor, for the singleton design pattern.
	 */
	private ProductGroupVault(){
		currentInstance = this;
	}

	/**
	 * Returns a reference to the only instance allowed for this class.
	 */
	public static synchronized ProductGroupVault getInstance(){
		if(currentInstance == null) currentInstance = new ProductGroupVault();
		return currentInstance;
	}

	/**
	 * Returns just one ProductGroup based on the query sent in. 
	 * If you need more than one returned use FindAll
	 * 
	 * @param query of form obj.attr = value
	 * 
	 * product.id = 5
	 * name = 'cancer'
	 * id = 2
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * 
	 */
	public ProductGroup find(String query, Object... params)  {
		return (ProductGroup)findPrivateCall(query);
	}
	
	
	/**
	 * Returns a list of Products which match the criteria
	 * 
	 * @param attribute 
	 * @param value
	 * 
	 */
	public ArrayList<ProductGroup> findAll(String query, Object... params) {
		return (ArrayList)this.findAllPrivateCall(query);
	}
	public int getLastIndex(){
		return (int)dataVault.size()+StorageUnitVault.getInstance().size();
	}
	public String getName(int id) {
		ProductGroup pg = this.get(id);
		if (pg != null) {
			return pg.getName();
		}else{
			return "";
		}
	}
	public ProductGroup get(int id){
		return (ProductGroup) this.getPrivateCall(id);
	}
	protected ProductGroup getNewObject(){
		return new ProductGroup();
	}
	protected ProductGroup getCopiedObject(IModel model){
		return new ProductGroup((ProductGroup)model);
	}
	
	/**
	 * Checks if the model passed in already exists in the current map
	 * - Must have a unique name in su
	 * 
	 * @param model
	 * @return Result of the check
	 */
	protected Result validateNew(IModel model){
		Result result = new Result();
		result = checkUniqueName((ProductGroup) model);
		if(result.getStatus() == false)
			return result;

        model.setValid(true);
		return result;
	}	
	/**
	 * Adds the ProductGroup to the map if it's new.  Should check before doing so.
	 * 
	 * @param model ProductGroup to add
	 * @return Result of request
	 */
	protected  Result saveNew(ProductGroup model){
        if(!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if(dataVault.isEmpty() && model._storageUnitVault.size()==0)
            id = 0;
        else
            id = (int)dataVault.size()+1+model._storageUnitVault.size();

        model.setId(id);
        model.setSaved(true);
        this.addModel(new ProductGroup(model));
        return new Result(true);
	}
	
	private Result checkUniqueName(ProductGroup model){
        //Null check
        if(model.getName() == null)
            return new Result(false, "Name can't be null");


        if(model.getName() == "")
            return new Result(false, "Name can't be empty");

        //Checks that the product group has a unique name in it's container
        //Return all the ProductGroups in the current level
		ArrayList<ProductGroup> myPGs = findAll("ParentIdString = " + model.getParentIdString());
		//Loop through those results and make sure the name is unique
		for(ProductGroup tempGroup : myPGs){
			if(tempGroup.getName().equals(model.getName()))
				return new Result(false,"Duplicate product in container");
		}

        model.setValid(true);
        return new Result(true);
	}
}

