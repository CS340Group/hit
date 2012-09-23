package common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class containing date/time functionality
 */
public final class QueryParser {
	private String query;
	
	private String objectName;
	private String objectAttr;
	private String value;
	
	/**
	 * Private Constructor.
	 */
	public QueryParser(String query) {
		this.query = query;
		this.Parse();
	}
	
	/**
	 * Helper method to parse out the required fields in the query.
	 * Expects the query to look like:
	 * obj.attr = value
	 */
	private void Parse(){
		Pattern p = Pattern.compile("((\\w*)\\.)?(\\w*)\\s=\\s(.*)");
		Matcher m = p.matcher(query);
		boolean matchFound = m.find();
		if(matchFound){
			objectName = m.group(2);
			objectAttr = m.group(3);
			value = m.group(4);
		} else {
			//If match isn't found then the query is in the wrong form.
			assert(matchFound == true); 
		}

	}
	
	
	public String getObjName(){
		return this.objectName;
	}
	public String getAttrName(){
		return this.objectAttr;
	}
	public String getValue(){
		return this.value;
	}
	public String getQuery(){
		return this.query;
	}

}

