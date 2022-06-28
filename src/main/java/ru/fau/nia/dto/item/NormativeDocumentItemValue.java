package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.entity.TableColumnHasRowField;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NormativeDocumentItemValue implements ItemValue {
    private DictionaryDto value;

    private final static List<Integer> INSPECTION_BODY_IDS = Arrays.asList(7, 8, 5, 12, 14, 15);
    private final static List<Integer> COLUMN_NUMBERS = Arrays.asList(3, 4);

    @Override
    public Type getType() {
        if (value.getId() == null) {
            return Type.manualNormativeDocument;
        }
        return Type.normativeDocument;
    }

    @Override
    public Object getDictionaryId() {
        return value.getId();
    }

    @Override
    public String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber) {
        String result = rowField.toString();
        if (result == null) {
            result = value.getCode();
        }
        if (Objects.isNull(result)) {
            result = value.getValue();
        }
        if (INSPECTION_BODY_IDS.contains(accreditationBodyTypeId) && COLUMN_NUMBERS.contains(columnNumber)) {
            return result;
        }
        return result;
    }

    @Override
    public boolean isNormDoc() {
        return true;
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
