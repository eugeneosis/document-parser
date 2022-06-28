package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.fau.nia.entity.TableColumnHasRowField;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextItemValue.class, name = "text"),
        @JsonSubTypes.Type(value = DictItemValue.class, name = "dict"),
        @JsonSubTypes.Type(value = DictItemValue.class, name = "dictionary"),
        @JsonSubTypes.Type(value = DictCodeItemValue.class, name = "code"),
        @JsonSubTypes.Type(value = NormativeDocumentItemValue.class, name = "normativeDocument"),
        @JsonSubTypes.Type(value = NormativeDocumentItemValue.class, name = "manualNormativeDocument"),
        @JsonSubTypes.Type(value = RangeItemValue.class, name = "measurementGroup"),
        @JsonSubTypes.Type(value = RangeItemValue.class, name = "manualMeasurementGroup"),
        @JsonSubTypes.Type(value = TextArrayItemValue.class, name = "textArray")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ItemValue {
    enum Type {
        text,
        dict,
        code,
        normativeDocument,
        manualNormativeDocument,
        measurementGroup,
        manualMeasurementGroup,
        textArray
    }

    Object getValue();

    @JsonIgnore
    default Type getType() {
        throw new RuntimeException(String.format("Unknown item value type(%s)", getClass()));
    }

    @JsonIgnore
    default boolean isNormDoc() {
        return false;
    }

    @JsonIgnore
    default Object getDictionaryId() {
        return null;
    }

    @JsonIgnore
    default String getCode() {
        if (getValue() instanceof DictCode) {
            return ((DictCode) getValue()).getCode();
        }
        return null;
    }

    @JsonIgnore
    String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber);

    @JsonIgnore
    String getStringValue();

    @JsonIgnore
    String getDictValue();
}
