package model.common;

import common.Result;
import model.reports.Ivisitor;

/**
 * IModel defines functionality that is supported by all models in the program.
 */
public interface IModel extends Comparable<IModel> {

    public boolean isDeleted();

    public int getId();

    public void invalidate();

    /**
     * Returns true if the model is saved in the vault, false otherwise.
     */
    public boolean isSaved();

    /**
     * Returns true if the model is able to be saved to the vault, false otherwise.
     */
    public boolean isValid();

    /**
     * Validates if the model is in a savable state.
     */
    Result validate();

    /**
     * If the Model has been validated then it must be saved.
     * This should always return a good result as you should validate before you save.
     */
    Result save();

    public Result setValid(boolean b);

    public Result delete();

    public Result unDelete();

    public Result setSaved(boolean b);

    public void accept(Ivisitor visitor);

    public int compareTo(IModel other);

}