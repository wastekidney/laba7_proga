package ru.itmo.Collection;

import ru.itmo.utils.Validatable;

public class Address implements Validatable{
    private String street; //Поле может быть null

    public Address(String street){
        this.street = street;
    }

    public String getStreet(String street){
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
}
