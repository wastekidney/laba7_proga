package ru.itmo.common.Collection.User;

import ru.itmo.common.Validatable.Validatable;

import java.io.Serializable;

public class User implements Validatable, Serializable {
    private int id;
    private final String name;
    private final String password;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", password=" + password + '}';
    }

    @Override
    public boolean validate() {
        return name != null && !name.trim().isEmpty()
                && password != null && !password.trim().isEmpty();
    }

    public void setId(int id) {
        this.id = id;
    }
}
