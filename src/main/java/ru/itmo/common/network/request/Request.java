package ru.itmo.common.network.request;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public abstract class Request implements Serializable {
    private final String name;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return name.getBytes();
    }
}
