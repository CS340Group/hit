package model.common.operator;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class OperatorFactory {
    private static final Map<String, Operator<?>> OPERATORS = new HashMap<String, Operator<?>>();

    static {
        OPERATORS.put("=String", new EqualToStringOperator());
        OPERATORS.put("!=String", new NotEqualToStringOperator());
        OPERATORS.put("=DateTime", new EqualToDateTimeOperator());
        OPERATORS.put("<DateTime", new LessThanDateTimeOperator());
        OPERATORS.put(">DateTime", new GreaterThanDateTimeOperator());
        OPERATORS.put("=Number", new EqualToNumberOperator());
        OPERATORS.put("!=Number", new NotEqualToNumberOperator());
        OPERATORS.put("<Number", new LessThanNumberOperator());
        OPERATORS.put(">Number", new GreaterThanNumberOperator());
    }

    public static Operator<?> getOperator(String someUserSpecifiedOp, Class<?> paramType) {
        String key = someUserSpecifiedOp;
        if (DateTime.class.isAssignableFrom(paramType)) {
            key += "DateTime";
        } else if (Number.class.isAssignableFrom(paramType)) {
            key += "Number";
        } else if (String.class.isAssignableFrom(paramType)) {
            key += "String";
        }
        return OPERATORS.get(key);
    }

    //Operators
    public static class EqualToStringOperator implements Operator<String>{
        @Override
        public boolean execute(String lhs, String rhs) {
            return lhs.equals(rhs);
        }
    }

    public static class NotEqualToStringOperator implements Operator<String>{
        @Override
        public boolean execute(String lhs, String rhs) {
            return !lhs.equals(rhs);
        }
    }

    public static class EqualToDateTimeOperator implements Operator<DateTime>{

        @Override
        public boolean execute(DateTime lhs, DateTime rhs) {
            return lhs.isEqual(rhs);
        }
    }

    public static class LessThanDateTimeOperator implements Operator<DateTime>{

        @Override
        public boolean execute(DateTime lhs, DateTime rhs) {
            return lhs.isBefore(rhs);
        }
    }

    public static class GreaterThanDateTimeOperator implements Operator<DateTime>{

        @Override
        public boolean execute(DateTime lhs, DateTime rhs) {
            return lhs.isAfter(rhs);
        }
    }

    public static class EqualToNumberOperator implements Operator<Number>{

        @Override
        public boolean execute(Number lhs, Number rhs) {
            return lhs.doubleValue() == rhs.doubleValue();
        }
    }

    public static class NotEqualToNumberOperator implements Operator<Number>{

        @Override
        public boolean execute(Number lhs, Number rhs) {
            return lhs.doubleValue() != rhs.doubleValue();
        }
    }

    public static class LessThanNumberOperator implements Operator<Number>{

        @Override
        public boolean execute(Number lhs, Number rhs) {
            return lhs.doubleValue() < rhs.doubleValue();
        }
    }

    public static class GreaterThanNumberOperator implements Operator<Number>{

        @Override
        public boolean execute(Number lhs, Number rhs) {
            return lhs.doubleValue() > rhs.doubleValue();
        }
    }

}
