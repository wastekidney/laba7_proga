package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class AuthenticationRequest extends Request{

    public AuthenticationRequest(User user) {
        super("AuthenticationRequest", user);

    }
}
