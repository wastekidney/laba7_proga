package ru.itmo.common.network.request;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.User.User;

import java.io.Serializable;

public class AddRequest extends Request implements Serializable {
    public final Product product;
    public AddRequest(Product product, User user) {
        super("add", user);
        this.product = product;

    }
    public Product getProduct() {
        return product;
    }
}
