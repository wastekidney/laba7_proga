package ru.itmo.common.network.request;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.User.User;

import java.io.Serializable;

public class AddIfMinRequest extends Request implements Serializable {
    public final Product product;
    public AddIfMinRequest(Product product, User user) {
        super("add_if_min", user);
        this.product = product;
    }
}
