package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.fau.nia.entity.TableColumnHasRowField;
import ru.parma.fgis.oa.data.entity.conf.TableColumnHasRowField;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseItem.class, name = "base"),
        @JsonSubTypes.Type(value = BaseItem.class, name = "dictionary"),
        @JsonSubTypes.Type(value = NormativeDocumentItem.class, name = "normativeDocument")
})
public interface Item extends ColumnDivided {

    String getName();

    ItemValue getValue();

    Boolean getIsRecommended();

    @JsonIgnore
    default String getType() {
        if (this instanceof BaseItem) {
            return "base";
        }
        if (this instanceof NormativeDocumentItem) {
            return "normativeDocument";
        }
        throw new RuntimeException("Unknown item type");
    }

    @JsonIgnore
    default boolean isNormDoc() {
        return getValue().isNormDoc();
    }

    @JsonIgnore
    default Object getDictionaryId() {
        return null;
    }

    @JsonIgnore
    String getCode();

    @JsonIgnore
    String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber);

    @Override
    @JsonIgnore
    default String getColumn1() {
        return null;
    }

    @Override
    @JsonIgnore
    default String getColumn2() {
        return null;
    }
}
