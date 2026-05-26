package ru.itmo.common.Collection;

import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Validatable.Validatable;

import java.io.Serializable;
import java.util.Objects;

public class Organization implements Validatable, Serializable{
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Double annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле не может быть null



    public Organization(String name, Double annualTurnover, OrganizationType type, Address officialAddress) {
        this.id = null;
        this.name = name;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
    }
    public String getName(){
        return this.name;
    }
    public Integer getId() {
        return this.id;
    }
    public Double getAnnualTurnover(){
        return annualTurnover;
    }
    public OrganizationType getType(){
        return type;
    }



    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (type == null) return false;
        if (officialAddress == null) return false;
        return true;
    }
    @Override
    public String toString(){
        return "{\n" + "id: " + id +
                ",\nname: " + name +
                ",\nannualTurnover: " + annualTurnover +
                ",\ntype: " + type +
                ",\nofficialAddress: " + officialAddress
                ;
    }

    public void update(Organization other) {
        this.name = other.name;
        this.annualTurnover = other.annualTurnover;
        this.type = other.type;
        this.officialAddress = other.officialAddress;
    }

    public Address getOfficialAddress() {

        return this.officialAddress;
    }

    public void setName(String organizationName) {
        this.name = organizationName;
    }

    public void setAnnualTurnover(double turnover) {
        this.annualTurnover = turnover;
    }

    public void setType(OrganizationType organizationType) {
        this.type = organizationType;
    }

    public void setOfficialAddress(Address address) {
        this.officialAddress = address;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}