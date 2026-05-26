package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class RemoveLowerRequest extends Request {
    public final String price;
    public RemoveLowerRequest(String price,  User user) {
        super("remove_lower",  user);
        this.price = price;
    }
}
