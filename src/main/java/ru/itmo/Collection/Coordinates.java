package ru.itmo.Collection;

import ru.itmo.utils.Validatable;

public class Coordinates implements Validatable{
    private Double x; //Поле не может быть null
    private float y;

    public Coordinates(Double x, float y) {
        this.x = x;
        this.y = y;
    }


    public Double getX(){
        return x;
    }
    public float getY(float y){
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
}
