package model.common;

import java.util.SortedMap;
import java.util.TreeMap;

import common.Result;

public abstract class Vault implements IVault {

	
	protected static SortedMap<Integer, IModel> dataVault = new TreeMap<Integer, IModel>();
	
	/**
	 * Constructor.
	 * 
	 * 
	 */
	public Vault(){
		return;
	}

    public static int size(){
        return dataVault.size();
    }

    public static void clear(){
        dataVault.clear();
    }

}