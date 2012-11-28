package model.storage;

import model.product.ProductVault;

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
	
	/*
	 * Method which should be ran when the HIT program starts
	 */
	public void hitStart(){
		this.currentFactory.getItemDAO().loadAllData();
		this.currentFactory.getProductDAO().loadAllData();
		this.currentFactory.getProductGroupDAO().loadAllData();
		this.currentFactory.getStorageUnitDAO().loadAllData();
	}
	
	/*
	 * Method which should be ran when the HIT program closes
	 */
	public void hitClose(){
		this.currentFactory.getItemDAO().saveAllData();
		this.currentFactory.getProductDAO().saveAllData();
		this.currentFactory.getProductGroupDAO().saveAllData();
		this.currentFactory.getStorageUnitDAO().saveAllData();
	}
	
	
	/*
	 * TODO: This should commit any current transaction and return a new one
	 * It should get the transaction from one of the DAO's?
	 */
	public void GetNewTransaction(){
		
	}
	
	/*
	 * TODO: This should just return the current transaction if one exists
	 */
	public void GetCurrentTransaction(){
		
	}

}
