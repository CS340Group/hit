package common;

/**
 * Represents the result of an operation that returns a value.
 *
 * @param <T> Type of operation return value.
 */
public class ValueResult<T> extends Result {
    /**
     * Value attribute. Contains the return value of the operation.
     */
    private T _value;

    /**
     * Constructor.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post getStatus() == false, getMessage() == "", getValue() == null}
     */
    public ValueResult() {
        super();

        setValue(null);
    }

    /**
     * Constructor.
     *
     * @param status Initial value of Status attribute.
     *               <p/>
     *               {@pre None}
     *               <p/>
     *               {@post getStatus() == status, getMessage() == "", getValue() == null}
     */
    public ValueResult(boolean status) {
        super(status);

        setValue(null);
    }

    /**
     * Constructor.
     *
     * @param status  Initial value of Status attribute.
     * @param message Initial value of Message attribute.
     *                <p/>
     *                {@pre None}
     *                <p/>
     *                {@post getStatus() == status, getMessage() == message, getValue() == null}
     */
    public ValueResult(boolean status, String message) {
        super(status, message);

        setValue(null);
    }

    /**
     * Constructor.
     *
     * @param status  Initial value of Status attribute.
     * @param message Initial value of Message attribute.
     * @param value   Initial value of Value attribute.
     *                <p/>
     *                {@pre None}
     *                <p/>
     *                {@post getStatus() == status, getMessage() == message, getValue() == value}
     */
    public ValueResult(boolean status, String message, T value) {
        super(status, message);

        setValue(value);
    }

    /**
     * Copy Constructor.
     *
     * @param other Object to be copied.
     *              <p/>
     *              {@pre other != null}
     *              <p/>
     *              {@post this is a copy of other}
     */
    public ValueResult(ValueResult<T> other) {
        super(other);

        setValue(other.getValue());
    }

    /**
     * Sets value of Value attribute.
     *
     * @param value New value of Value attribute
     *              <p/>
     *              {@pre None}
     *              <p/>
     *              {@post getValue() == value}
     */
    public void setValue(T value) {
        _value = value;
    }

    /**
     * Returns value of Value attribute.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post Returns value of Value attribute.}
     */
    public T getValue() {
        return _value;
    }

}

