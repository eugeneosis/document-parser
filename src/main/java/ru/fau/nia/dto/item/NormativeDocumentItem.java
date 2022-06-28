package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.entity.TableColumnHasRowField;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NormativeDocumentItem extends BaseItem {
    String sectionNumber;

    @Override
    public String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append(getValue().getPrintedFormValue(rowField, accreditationBodyTypeId, columnNumber));

        if (sectionNumber != null && !sectionNumber.isEmpty()) {
            builder.append(", ");
            builder.append(sectionNumber);
        }
        return builder.toString();
    }
}
