package ru.fau.nia.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DictNormDocType implements DictBase {

    private Integer id;

    @JsonProperty("name")
    private String value;

    private String shortName;

    @Override
    public Object getIdDict() {
        return id;
    }

}
