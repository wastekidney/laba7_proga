package ru.itmo.common.network.response;

public class InfoResponse extends Response{
    private final String info;
    public InfoResponse(String info, String messageError) {
        super("info", messageError);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
