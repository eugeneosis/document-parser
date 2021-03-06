package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.entity.TableColumnHasRowField;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextArrayItemValue implements ItemValue {

    @JsonDeserialize(using = StringDeserializer.class)
    private String value;

    @Override
    public String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber) {
        return value;
    }

    @Override
    public Type getType() {
        return Type.textArray;
    }

    @Override
    public String getStringValue() {
        return null;
    }

    @Override
    public String getDictValue() {
        return null;
    }
}
