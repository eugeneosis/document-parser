package ru.fau.nia.dto.ral;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.TreeSet;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RalField {
    private String name;

    private Collection<String> values = new TreeSet<>();

    public RalField(String name) {
        this.name = name;
    }

    public void addValue(String value) {
        if (value == null) {
            return;
        }
        values.add(value);
    }
}
