package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class ClearRequest extends Request{
    public ClearRequest(User user) {
        super("clear", user);
    }
}
