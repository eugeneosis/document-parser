package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetrologicalCharacteristicCollection extends ArrayList<MetrologicalCharacteristic> {
    @Override
    public String toString() {
        return stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
