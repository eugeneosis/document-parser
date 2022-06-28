package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Адрес из ФИАС. Может содержать любой из элементов адреса, кроме: название страны, номер дома и номер квартиры
 */
@Data
public class AddressFiasAddrobj implements DictBase {


    private String id;      // GUID

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String offname;     // Наименование

    @JsonIgnore
    private String shortname;   // Приставка/суффикс наименования, например, ул., р-н, обл., край и т.д.

    @JsonIgnore
    private String aolevel;     // Уровень адреса (1 - субъект РФ, 3 - район и т.д. см. REST API)

    @JsonIgnore
    private String parentguid;  // GUID родителя

    @Override
    public Object getIdDict() {
        return id;
    }

    @JsonProperty("name")
    @Override
    public String getValue() {
        if (name == null) {
            return Stream.of(offname, shortname).filter(StringUtils::isNotEmpty).collect(Collectors.joining(" "));
        }
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

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

    public AddressFiasAddrobj() {
    }

    public AddressFiasAddrobj(String id) {
        this.id = id;
    }
}
