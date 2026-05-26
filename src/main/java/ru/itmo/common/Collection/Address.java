package ru.itmo.common.Collection;

import ru.itmo.common.Validatable.Validatable;

import java.awt.*;
import java.io.Serializable;

public class Address implements Validatable, Serializable {
    private String street; //Поле может быть null

    public Address(String street){
        this.street = street;
    }

    public String getStreet(){
        return street;
    }

    @Override
    public boolean validate() {
        if (street == null) return false;
        return true;
    }
    @Override
    public String toString(){
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
