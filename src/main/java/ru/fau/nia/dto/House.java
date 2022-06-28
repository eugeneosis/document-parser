package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class House {
    private String id;
    private String name;
    private String postcode;
    private String buildNum;
    private Integer possession;
    private String strStatus;
    private String strucNum;

    @JsonIgnore
    public UUID getFilteredId() {
        if (id.isEmpty() || "0".equals(id)) {
            return null;
        }

        return UUID.fromString(id);
    }

    @JsonIgnore
    public String getFilteredValue() {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return name;
    }
}
