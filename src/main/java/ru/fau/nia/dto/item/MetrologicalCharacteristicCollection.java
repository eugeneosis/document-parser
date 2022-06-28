package ru.fau.nia.dto.item;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MetrologicalCharacteristicCollection extends ArrayList<MetrologicalCharacteristic> {
    @Override
    public String toString() {
        return stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
