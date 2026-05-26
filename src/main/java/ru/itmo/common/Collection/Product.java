package ru.itmo.common.Collection;

import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Validatable.Validatable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Product implements Validatable, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private String partNumber; //Длина строки должна быть не меньше 26, Длина строки не должна быть больше 67, Значение этого поля должно быть уникальным, Поле не может быть null
    private double manufactureCost;
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private Organization manufacturer; //Поле не может быть null

    private int userId;
    public Product(String name, Coordinates coordinates,
                   java.time.ZonedDateTime creationDate, float price,
                   String partNumber, double manufactureCost,
                   UnitOfMeasure unitOfMeasure, Organization manufacturer){
        this.id = 0;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
    }

    public long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public java.time.ZonedDateTime getCreationDate(java.time.ZonedDateTime creationDate) {
        return creationDate;
    }
    public float getPrice() {
        return this.price;
    }
    public String getPartNumber() {
        return partNumber;
    }
    public double getManufactureCost() {
        return manufactureCost;
    }
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }
    public Organization getManufacturer() {
        return this.manufacturer;
    }
    public String getAddress(Address address) {
        return address.getStreet();
    }


    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (price <= 0) return false;
        if (partNumber.length() < 26 || partNumber.length() > 67) return false;
        if (unitOfMeasure == null) return false;
        if (manufacturer == null) return false;
        return true;
    }

    @Override
    public String toString(){
        return "{ \n" + "id: " + id + ", \nname: " + name +
                ",\ncoordinates: " + "{\n" + coordinates + "\n} \n" + "creationDate: " + creationDate +
                ",\nprice: " + price + ",\npartNumber: " + partNumber + ",\nmanufactureCost" + manufactureCost +
                ",\nunitOfMeasure: " + unitOfMeasure + ",\nmanufacturer: " + manufacturer + "}";

    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }


    public void addUpdate(Product product, int userId) {
        product.id = 0;
        product.setUserId(userId);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime zonedDateTime) {
        this.creationDate = zonedDateTime;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public void setManufactureCost(double manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setManufacturer(Organization organization) {
        this.manufacturer = organization;
    }

    public Product(long id, String name, Coordinates coordinates,
                   java.time.ZonedDateTime creationDate, float price,
                   String partNumber, Double manufactureCost,
                   UnitOfMeasure unitOfMeasure, Organization manufacturer,
                   int userId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
        this.userId = userId;
    }
}