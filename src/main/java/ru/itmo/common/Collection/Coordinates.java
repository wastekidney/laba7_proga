package ru.itmo.common.Collection;

import ru.itmo.common.Validatable.Validatable;

import java.io.Serializable;

public class Coordinates implements Validatable, Serializable {
    private Double x; //Поле не может быть null
    private float y;

    public Coordinates(Double x, float y) {
        this.x = x;
        this.y = y;
    }


    public Double getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    @Override
    public boolean validate() {
        if (x == null) return false;
        return true;
    }

    @Override
    public String toString(){
        return "x: " + x + ",\ny: " + y;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
}
