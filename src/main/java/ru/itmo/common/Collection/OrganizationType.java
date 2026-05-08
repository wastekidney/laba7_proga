package ru.itmo.common.Collection;

import java.util.Arrays;

public enum OrganizationType {
    PUBLIC,
    GOVERNMENT,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;



    public static String names() {
        return String.join(", ", Arrays.stream(values())
                .map(Enum::name)
                .toArray(String[]::new));
    }
}