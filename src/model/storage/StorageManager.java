package model.storage;

import model.common.IModel;

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
		String className = model.getClass().getSimpleName();
		if(className.equals("Item"))
			return this.currentFactory.getItemDAO();
		if(className.equals("Product"))
			return this.currentFactory.getProductDAO();
		if(className.equals("ProductGroup"))
			return this.currentFactory.getProductGroupDAO();
		if(className.equals("StorageUnit"))
			return this.currentFactory.getStorageUnitDAO();
		
		return null;
	}
	
	/*
	 * Method which should be ran when the HIT program starts
	 */
	public void hitStart(){
        this.currentFactory.initializeConnection();
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
