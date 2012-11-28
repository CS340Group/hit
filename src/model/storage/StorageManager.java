package model.storage;

public class StorageManager {
	static StorageManager currentInstance;
	IDAOFactory currentFactory;
	/**
	 * Private constructor, for the singleton design pattern.
	 */	
	private StorageManager(){
		currentInstance = this;
	}
	
	/**
	 * Returns a reference to the only instance allowed for this class.
	 */
	public static synchronized StorageManager getInstance(){
		if(currentInstance == null) currentInstance = new StorageManager();
		return currentInstance;
	}
	
	public void setFactory(IDAOFactory factory){
		currentFactory = factory;
	}
	public IDAOFactory getFactory(){
		return this.currentFactory;
	}
	public IStorageDAO getAppropriateDAO(IModel model){
		if(model.getClass().equals("Item"))
			return this.currentFactory.getItemDAO();
		if(model.getClass().equals("Product"))
			return this.currentFactory.getProductDAO();
		if(model.getClass().equals("ProductGroup"))
			return this.currentFactory.getProductGroupDAO();
		if(model.getClass().equals("StorageUnit"))
			return this.currentFactory.getStorageUnitDAO();
		
		return null;
	}
	
	/*
	 * Method which should be ran when the HIT program starts
	 */
	public void hitStart(){
		this.currentFactory.getItemDAO().loadAllData();
		this.currentFactory.getProductDAO().loadAllData();
		this.currentFactory.getProductGroupDAO().loadAllData();
		this.currentFactory.getStorageUnitDAO().loadAllData();
	}
	
	/**
	 * Method which should be ran when the HIT program closes
	 */
	public void hitClose(){
		this.currentFactory.getItemDAO().saveAllData();
		this.currentFactory.getProductDAO().saveAllData();
		this.currentFactory.getProductGroupDAO().saveAllData();
		this.currentFactory.getStorageUnitDAO().saveAllData();
	}

}
