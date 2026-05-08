package ru.itmo.common.network.request;

public class FilterContainsNameRequest extends Request{
    public final String substringName;
    public FilterContainsNameRequest(String substringName) {
        super("filter_contains_name");
        this.substringName = substringName;
    }
}
