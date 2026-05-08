package ru.itmo.common.network.request;

import ru.itmo.common.Collection.Product;

import java.io.Serializable;

public class AddIfMinRequest extends Request implements Serializable {
    public final Product product;
    public AddIfMinRequest(Product product) {
        super("add_if_min");
        this.product = product;
    }
}
