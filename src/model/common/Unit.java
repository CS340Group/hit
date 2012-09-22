package model.common;

import common.Result;

public class Unit {

    private float _amount;
    private String _unit;

    //change u to enum
    public Unit(float a, String u){
        this._amount = a;
        this._unit = u;
    }

    public float getAmount(){
        return _amount;
    }

    public Result setAmount(float a){
        _amount = a;
        return new Result(true, "Amount was set successfully");
    }

    public String getUnit(){
        return _unit;
    }

    public Result setUnit(String u){
        _unit = u;
        return new Result(true, "Unit was set successfully");
    }

    @Override
    public String toString(){
        return _amount + " " + _unit;
    }
}
