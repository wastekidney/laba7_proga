package ru.itmo.common.network.request;

public class RemoveGreaterRequest extends Request{
    public final String price;
    public RemoveGreaterRequest(String price) {
        super("remove_greater");
        this.price = price;
    }
}
