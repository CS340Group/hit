package model.common.operator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class OperatorFactory {
    private static final Map<String, Operator<?>> OPERATORS = new HashMap<String, Operator<?>>();

    static {
        OPERATORS.put("=String", new EqualToOperator());
        //OPERATORS.put("<Number", new LessThanNumOperator());
        //OPERATORS.put("==Number", new EqualToNumOperator());
        //OPERATORS.put("<String", new LessThanStringOperator());
    }

    public static Operator<?> getOperator(String someUserSpecifiedOp, Class<?> paramType) {
        String key = someUserSpecifiedOp;
        if (Number.class.isAssignableFrom(paramType)) {
            key += "Number";
        } else if (String.class.isAssignableFrom(paramType)) {
            key += "String";
        }
        return OPERATORS.get(key);
    }

    //Operators
    public static class EqualToOperator implements Operator<String>{
        @Override
        public boolean execute(String lhs, String rhs) {
            return lhs.equals(rhs);
        }
    }


}
