package model.common;

import common.Result;
/**
 * The Barcode class represents a UPC-A barcode. It holds a 12-number unique
 * integer, and is capable of rendering the graphical representation of the 
 * barcode for printing.
 */
public class Barcode{
	private int _number;
	
	public Barcode(int number){
		_number = number;
	}
	
	/**
	 * Sets the number of the barcode equal to the parameter value.
	 */
	public void setNumber(int number){
		_number = number;
	}

	/**
	 * Returns the barcode's integer.
	 */
	public int getNumber(int number){
		return _number;
	}

	public string toString(){
		return (String)_number;
	}

}