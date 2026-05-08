package ru.itmo.common.network.request;

import ru.itmo.common.Collection.Product;

public class UpdateIdRequest extends Request {
    public final Product newProduct;
    public final String id;
    public UpdateIdRequest(String id,  Product newProduct) {
        super("update_id");
        this.id = id;
        this.newProduct = newProduct;
    }
}
