package ru.itmo.common.network.request;

public class RemoveByIdRequest extends Request{
    public final String id;
    public RemoveByIdRequest(String id) {
        super("remove_by_id");
        this.id = id;

    }
}
