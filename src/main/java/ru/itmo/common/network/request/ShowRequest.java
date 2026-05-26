package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;
import ru.itmo.common.network.response.Response;

public class ShowRequest extends Request {
    public ShowRequest(User user) {
        super("show",  user);
    }
}
