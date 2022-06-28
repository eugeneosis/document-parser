package ru.fau.nia.dto.item;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import ru.fau.nia.entity.TableColumnHasRowField;

@Setter
@Getter
@EqualsAndHashCode
public class DictItemValue implements ItemValue {
    private DictionaryDto value;

    @Override
    public Type getType() {
        return Type.dict;
    }

    @Override
    public Object getDictionaryId() {
        return value.getId();
    }

    @Override
    public String getCode() {
        return value.getCode();
    }

    @Override
    public boolean isNormDoc() {
        return value.getNormDocType() != null;
    }

    public String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber) {
        final StringBuilder builder = new StringBuilder();
        String result = rowField.toString();
        if (result == null) {
            result = value.getValue();
        }
        builder.append(result);
        if (StringUtils.isNotEmpty(value.getSectionNumber())) {
            builder.append(" ");
            builder.append(value.getSectionNumber());
        }
        return builder.toString();
    }

    @Override
    public String getStringValue() {
        return value.getValue();
    }

    @Override
    public String getDictValue() {
        return value.getDict();
    }
}
