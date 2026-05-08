package ru.itmo.common.network.request;

public class RemoveLowerRequest extends Request {
    public final String price;
    public RemoveLowerRequest(String price) {
        super("remove_lower");
        this.price = price;
    }
}
