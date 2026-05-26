package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class HelpRequest extends Request{

    public HelpRequest(User user) {
        super("help", user);
    }
}
