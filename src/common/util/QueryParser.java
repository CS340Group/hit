package common.util;

import model.common.operator.OperatorFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class containing date/time functionality
 */
public final class QueryParser {
    private String query;

    private String objectName;
    private String objectAttr;
    private Object value;
    private String operator;

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
    private void Parse() {
        Pattern p = Pattern.compile("((\\w*)\\.)?(\\w*)\\s(.*)\\s(.*)");
        Matcher m = p.matcher(query);
        OperatorFactory c = new OperatorFactory();
        boolean matchFound = m.find();
        if (matchFound) {
            objectName = m.group(2);
            objectAttr = m.group(3);
            operator = m.group(4);
            value = m.group(5);
        } else {
            //If match isn't found then the query is in the wrong form.
            assert (matchFound == true);
        }

    }


    public String getObjName() {
        return this.objectName;
    }

    public String getAttrName() {
        return this.objectAttr;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object val) {
        this.value = val;
    }

    public String getQuery() {
        return this.query;
    }

    public String getOperator() {
        return this.operator;
    }
}

