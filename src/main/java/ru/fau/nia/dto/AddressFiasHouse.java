package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.fau.nia.entity.DictBase;

/**
 * Адрес из ФИАС. Номер дома
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressFiasHouse implements DictBase {


    private String id;          // GUID

    @JsonProperty("name")
    private String value;       // Номер дома

    @JsonIgnore
    private String aoguid;  // GUID родителя

    private String buildNum;

    @Override
    public Object getIdDict() {
        return id;
    }

    @Override
    public String getValue() {
        return value;
    }

    public AddressFiasHouse() {
    }

    public AddressFiasHouse(String id, String value) {
        this.id = id;
        this.value = value;
    }
}
