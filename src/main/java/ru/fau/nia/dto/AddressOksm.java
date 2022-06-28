package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * Страна
 */
@Data
public class AddressOksm implements DictBase {

    private String id;     // Код страны

    @JsonProperty("name")
    private String value;  // Краткое наименование страны

    private String shortName;

    @Override
    public Object getIdDict() {
        return id;
    }

    @Override
    public String getValue() {
        return value;
    }

    public AddressOksm() {
    }

    public AddressOksm(String id, String value) {
        this.id = id;
        this.value = value;
    }
}
