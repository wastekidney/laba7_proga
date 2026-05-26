package ru.itmo.common.network.request;

import ru.itmo.common.Collection.Product;
import ru.itmo.common.Collection.User.User;

public class UpdateIdRequest extends Request {
    public final Product newProduct;
    public final String id;
    public UpdateIdRequest(String id,  Product newProduct,  User user) {
        super("update_id", user);
        this.id = id;
        this.newProduct = newProduct;
    }
}
