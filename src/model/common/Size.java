package model.common;

import common.Result;

public class Size {
    // This is to be able to set the units with consistency. 
    public enum Unit {count, lbs, oz, g, kg, gallons, quarts, pints,
    floz, liters}

    private float _amount;
    private Unit _unit;

    //change u to enum
    public Size(float a, Unit u){
        this._amount = a;
        this._unit = u;
    }

    public float getAmount(){
        return _amount;
    }

    public Result setAmount(float a){
        float oldAmt = _amount;
        _amount = a;
        Result valid = validate();
        if (!valid.getStatus()){
            _amount = oldAmt;
            return valid;
        }
        return new Result(true, "Amount was set successfully");
    }

    public Unit getUnit(){
        return _unit;
    }

    public Result setUnit(Unit u){
        Unit oldUnit = _unit;
        _unit = u;
        Result valid = validate();
        if (!valid.getStatus()){
            _unit = oldUnit;
            return valid;
        }
        return new Result(true, "The unit was set successfully");
    }

    public Result validate(){
        if (_amount < 0){
            return new Result(false, "The amount must not be negative.");
        }
        if (_unit == Unit.count && _amount != 1.0){
            return new Result(false, "If unit=count, amount must be 1");
        }
        return new Result(true, "You've got yourself a valid unit.");
    }

    @Override
    public String toString(){
        return _amount + " " + _unit;
    }
}
