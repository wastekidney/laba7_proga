package ru.itmo.common.Collection;

import java.util.Arrays;
import java.util.Comparator;

public enum UnitOfMeasure {
    KILOGRAMS,
    METERS,
    PCS,
    LITERS,
    GRAMS;

    public static String names() {
        return String.join(", ", Arrays.stream(values())
                .map(Enum::name)
                .toArray(String[]::new));

    }
    public static String nameReserved () {
        return String.join(", ", Arrays.stream(values())
                .map(Enum::name)
                .sorted(Comparator.reverseOrder())
                .toArray(String[]::new));
    }
}
