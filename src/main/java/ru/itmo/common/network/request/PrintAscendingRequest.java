package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class PrintAscendingRequest extends Request{
    public PrintAscendingRequest(User user) {
        super("print_ascending",  user);
    }
}
