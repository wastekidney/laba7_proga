package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class FilterContainsNameRequest extends Request{
    public final String substringName;
    public FilterContainsNameRequest(String substringName, User user) {
        super("filter_contains_name", user);
        this.substringName = substringName;
    }
}
