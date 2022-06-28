package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.entity.TableColumnHasRowField;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseItem implements Item {
    private String name;
    private ItemValue value;
    private Boolean isRecommended;
    //todo add fields structure for mapping

    @Override
    public Object getDictionaryId() {
        return value.getDictionaryId();
    }

    @Override
    public String getCode() {
        return value.getCode();
    }

    @Override
    public String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber) {
        return value.getPrintedFormValue(rowField, accreditationBodyTypeId, columnNumber);
    }

    @Override
    @JsonIgnore
    public String getColumn1() {
        if (value instanceof ColumnDivided) {
            return ((ColumnDivided) value).getColumn1();
        }
        return null;
    }

    @Override
    @JsonIgnore
    public String getColumn2() {
        if (value instanceof ColumnDivided) {
            return ((ColumnDivided) value).getColumn2();
        }
        return null;
    }
}
