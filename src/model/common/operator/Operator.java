package model.common.operator;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 11/3/12
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Operator<T> {
    public boolean execute(T lhs, T rhs);
}
