package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class RemoveByIdRequest extends Request{
    public final String id;
    public RemoveByIdRequest(String id, User user) {
        super("remove_by_id", user);
        this.id = id;

    }
}
