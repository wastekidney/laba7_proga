package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class InfoRequest extends Request {

    public InfoRequest(User user) {
        super("info",  user);
    }
}
