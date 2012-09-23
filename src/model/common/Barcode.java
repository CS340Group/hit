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

	/* ======== Private methods ===== */
	/**
	 * Fill me in.
	 */
	private String generateCodeFromId(String id){
		List<Integer> digits = splitInt(id);
		String ret = addCheckDigit(digits);
		return ret;
	}

	/**
	 * Fill me in.
	 */
	private List<Integer> splitInt(String id){
		char[] chars = id.toCharArray();
		List<Integer> ret = new ArrayList<Integer>();
		for (char i : chars){
			ret.add(Integer.parseInt(Character.toString(i)));
		}
		return ret;
	}

	/**
	 * Todo, doc.
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
	 * Fill me in.
	 */
	private boolean validateCheckBit(){
		String checker = _code.substring(0, _code.length()-1);
		checker = generateCodeFromId(checker);
		return checker.equals(_code);
	}

	/* ======== Public methods ===== */
	/**
	 * Fill me in.
	 */
	public Barcode(){
		_code = "unset";
	}

    /**
     * Copy Constructor
     */
    public Barcode(Barcode b){
        _code = b.getCode();
    }

	/**
	 * Fill me in.
	 */
	public void setCode(String code){
		_code = code;
	}

	/**
	 * Fill me in.
	 */
	public String getCode(){
		return _code;
	}

	/**
	 * Fill me in.
	 */
	public String toString(){
		return getCode();
	}

	/**
	 * Fill me in.
	 */
	public Result validate(){
		if (!validateCheckBit()){
			return new Result(false, "The barcode is not valid.");
		}
		if (_code.length() != 12){
			return new Result(false, "The barcode must have exactly 12 chars.");
		}
		return new Result(true, "The barcode is valid.");
	}

	/**
	 * Fill me in.
	 */
	public Result makeAndSetCodeFromId(String id){
		if(id.length() > 11){
			return new Result(false, "The id for the barcode cannot be more "+
			                  			"than 11 characters long.");
		}
		while (id.length() < 11){
			id = "0" + id;
		}
		_code = generateCodeFromId(id);
		return new Result(true, "Code generated and set successfully.");
	}

	/* ======== Static methods ===== */
	/**
	 * Fill me in.
	 */
	public static Barcode newFromId(String id){
		Barcode b = new Barcode();
		Result r = b.makeAndSetCodeFromId(id);
		if (r.getStatus()) {
			return b;
		}else{
			/* TODO: Figure out what to do here, not exception! */
			throw new IllegalArgumentException("id more than 11 chars long.");
		}
	}
}
