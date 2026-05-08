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

    private static Integer nextId = 1;

    public Organization(String name, Double annualTurnover, OrganizationType type, Address officialAddress) {
        this.id = nextId;
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
    public OrganizationType getType(OrganizationType type){
        return type;
    }



    @Override
    public boolean validate() {
        if (id > 0 || id == null) return false;
        if (name == null) return false;
        if (annualTurnover == null) return false;
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
    public static void updateNextId(CollectionManager collectionManager) {
        int maxId = collectionManager.getStack().stream()
                .filter(Objects::nonNull)
                .map(Product::getManufacturer)
                .filter(Objects::nonNull)
                .mapToInt(Organization::getId)
                .max()
                .orElse(0);
        nextId = maxId + 1;
    }
    public void update(Organization other) {
        this.name = other.name;
        this.annualTurnover = other.annualTurnover;
        this.type = other.type;
        this.officialAddress = other.officialAddress;
    }


}