package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.entity.TableColumnHasRowField;

@Setter
@Getter
@EqualsAndHashCode
public class RangeItemValue implements ItemValue, ColumnDivided {
    private Range value;

    @Override
    public Type getType() {
        if (value.getId() == null) {
            return Type.manualMeasurementGroup;
        }
        return Type.measurementGroup;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber) {
        return rowField.toString();
    }

    @JsonIgnore
    public String getColumn1() {
        return value.getValue();
    }

    @JsonIgnore
    public String getColumn2() {
        return value.getRangeList().toString();
    }

    @Override
    public String getStringValue() {
        return value.getValue();
    }

    @Override
    public String getDictValue() {
        return null;
    }
}
