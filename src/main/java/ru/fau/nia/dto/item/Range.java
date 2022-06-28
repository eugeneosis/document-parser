package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Range {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String value;

    private MetrologicalCharacteristicCollection rangeList;
}
