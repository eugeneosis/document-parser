package ru.fau.nia.dto.item;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.entity.TableColumnHasRowField;

@Setter
@Getter
@EqualsAndHashCode
public class DictCodeItemValue implements ItemValue {
    private DictionaryDto value;

    @Override
    public Type getType() {
        return Type.code;
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
        String result = rowField.toString();
        if (result == null) {
            result = value.getCode();
        }

        return result;
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
