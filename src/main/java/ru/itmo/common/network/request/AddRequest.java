package ru.itmo.common.network.request;

import ru.itmo.common.Collection.Product;

import java.io.Serializable;

public class AddRequest extends Request implements Serializable {
    public final Product product;
    public AddRequest(Product product) {
        super("add");
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }
}
