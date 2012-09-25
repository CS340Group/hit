package model.common;

import common.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * The Barcode class represents a UPC-A barcode. It holds a 12-number unique
 * integer, and is capable of rendering the graphical representation of the 
 * barcode for printing.
 */
public class Barcode{
	private String _code;
	private boolean _set;
	private boolean _valid;

	/**
	 * Constructor. Builds an invalid barcode object.
	 */
	public Barcode(){
		_code = "-1";
		_set = false;
	}

	/**
	 * Constructor. Builds an invalid barcode object from a string arg.
	 */
	public Barcode(String code){
		Result r;
		if(code.length() == 12){
			r = setCode(code);
		}else{
			r = setCodeFromId(code);
		}
		if (r.getStatus()){
			_set = true;
			_valid = true;
		}else{
			_set = false;
			_valid = false;
		}
	}

	/* ======== Private methods ===== */
	/**
	 * Accepts a list of integers. The list should be no longer than 11 entries.
	 * The valid checkbit is calculated, appended to the list, and a string of
	 * the total list is returned.
	 */
	private String addCheckDigit(List<Integer> d){
		// int sumOdd = (d[1] + d[3] + d[5] + d[7] + d[9])*3;
		int odds = 0;
		for (int i : new int[] {1, 3, 5, 7, 9, 11}){
			odds += d.get(i-1);
		}
		odds *= 3;
		int evens = 0;
		for(int i : new int[] {2, 4, 6, 8, 10}){
			evens += d.get(i-1);
		}

		int result = evens + odds;
		int mod = result % 10;
		int checkbit;
		if (mod != 0) {
			checkbit = 10 - mod;
		}else{
			checkbit = mod;
		}
		d.add(checkbit);
		String retstr = "";
		for (int i : d){
			retstr+=i;
		}
		return retstr;
	}

	/**
	 * Generates a valid code string from an id string.
	 */
	private String generateCodeFromId(String id){
		List<Integer> digits = splitToInts(id);
		String ret = addCheckDigit(digits);
		return ret;
	}

	/**
	 * Splits some string into a list of its memebers.
	 */
	private List<Integer> splitToInts(String id){
		char[] chars = id.toCharArray();
		List<Integer> ret = new ArrayList<Integer>();
		for (char i : chars){
			ret.add(Integer.parseInt(Character.toString(i)));
		}
		return ret;
	}

	/**
	 * Makes sure a whole string made of numbers.
	 */
	private Result verifyOnlyNumbers(String s){
		for (char c : s.toCharArray()) {
			try {
				Integer.parseInt(Character.toString(c));
			}catch(NumberFormatException e){
				return new Result(false, "A non-number character was encountered.");
			}
		}
		return new Result(true, "the string is only composed of numbers.");
	}

	/**
	 * The check bit is the last digit in the barcode. It is generated by an
	 * algorithm to make sure that the barcode is entered correctly.
	 */
	private boolean validateCheckBit(String str){
		String checker = str.substring(0, str.length()-1);
		checker = generateCodeFromId(checker);
		return checker.equals(str);
	}

	/* ======== Public methods ===== */
    /**
     * Copy Constructor
     */
    public Barcode(Barcode b){
        _code = b.getCode();
    }

	/**
	 * Accepts a String. For the Barcode to be valid the string must contain
	 * only 12 integers. The last integer must be a valid check digit.
	 */
	public Result setCode(String code){
		if(code.length() > 12){
			return new Result(false, "Code is too long.");
		}
		if(!verifyOnlyNumbers(code).getStatus()){
			return new Result(false, "Non-number characters in string.");
		}
		if(!validateCheckBit(code)){
			return new Result(false, "Not a valid code.");
		}
		_code = code;
		_set = true;
		return new Result(true, "Code is valid and set.");
	}

	/**
	 * Accepts a string of integers no longer than 11 characters. A valid 
	 * barcode based upon the parameter is then generated and set as the
	 * code member variable.
	 */
	public Result setCodeFromId(String id){
		if(id.length() > 11){
			return new Result(false, "The id for the barcode cannot be more "+
			                  			"than 11 characters long.");
		}
		if(!verifyOnlyNumbers(id).getStatus()){
			return new Result(false, "Non-number characters in string.");
		}
		while (id.length() < 11){
			id = "0" + id;
		}
		_code = generateCodeFromId(id);
		_set = true;
		return new Result(true, "Code generated and set successfully.");
	}

	/**
	 * Returns the string representing the barcode.
	 */
	public String getCode(){
		return _code;
	}

	/**
	 * Same as getCode(), defined for functionality with other classes.
	 */
	public String toString(){
		return getCode();
	}

	/**
	 * Returns true if the Barcode has had a value set.
	 */
	public boolean isSet(){
		return _set;
	}

	/**
	 * Checks to make sure the pre-conditions for the barcode are met.
	 */
	public Result validate(){
		Result r = setCode(_code);
		return r;
	}
}
