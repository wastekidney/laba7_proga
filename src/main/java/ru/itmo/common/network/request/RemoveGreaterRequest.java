package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class RemoveGreaterRequest extends Request{
    public final String price;
    public RemoveGreaterRequest(String price,  User user) {
        super("remove_greater",  user);
        this.price = price;
    }
}
