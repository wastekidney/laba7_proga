package ru.itmo.common.Collection;

import ru.itmo.server.Managers.CollectionManager;
import ru.itmo.common.Validatable.Validatable;

import java.io.Serializable;
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

    public static long nextId = 1;

    public Product(String name, Coordinates coordinates,
                   java.time.ZonedDateTime creationDate, float price,
                   String partNumber, double manufactureCost,
                   UnitOfMeasure unitOfMeasure, Organization manufacturer){
        this.id = nextId;
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
    public Coordinates getCoordinates(Coordinates coordinates) {
        return coordinates;
    }
    public java.time.ZonedDateTime getCreationDate(java.time.ZonedDateTime creationDate) {
        return creationDate;
    }
    public float getPrice() {
        return this.price;
    }
    public String getPartNumber(String partNumber) {
        return partNumber;
    }
    public double getManufactureCost() {
        return this.manufactureCost;
    }
    public UnitOfMeasure getUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        return unitOfMeasure;
    }
    public Organization getManufacturer() {
        return this.manufacturer;
    }
    public String getAddress(Address address) {
        return address.getStreet();
    }
    public long getNextId() {
        return nextId;
    }
    public void setNextId(long nextId) {
        Product.nextId = nextId;
    }

    @Override
    public boolean validate() {
        if (id <= 0) return false;
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

    public static void updateNextId(CollectionManager collectionManager) {
        long maxId = collectionManager.getStack().stream()
                .filter(Objects::nonNull)
                .mapToLong(Product::getId)
                .max()
                .orElse(0L);
        nextId = maxId + 1;
    }

    public void update(Product product) {
        this.name = product.name;
        this.coordinates = product.coordinates;
        this.creationDate = product.creationDate;
        this.price = product.price;
        this.partNumber = product.partNumber;
        this.manufactureCost = product.manufactureCost;
        this.unitOfMeasure = product.unitOfMeasure;
        this.manufacturer.update(product.manufacturer);
    }

    public void addUpdate(Product product) {
        product.id = nextId;
        this.manufacturer.addUpdate(product.manufacturer);
    }

}