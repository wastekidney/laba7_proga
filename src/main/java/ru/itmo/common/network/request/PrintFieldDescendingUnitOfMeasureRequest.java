package ru.itmo.common.network.request;

import ru.itmo.common.Collection.User.User;

public class PrintFieldDescendingUnitOfMeasureRequest extends  Request {
    public PrintFieldDescendingUnitOfMeasureRequest(User user) {
        super("print_field_descending_unit_of_measure",  user);
    }
}
