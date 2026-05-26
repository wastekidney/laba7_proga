package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public abstract class Request implements Serializable {
    private final String name;
    private final User user;

    public Request(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public  User getUser() {
        return user;
    }
    public Integer getId(){
        return user.getId();
    }


    public byte[] getBytes() {
        return name.getBytes();
    }
}
