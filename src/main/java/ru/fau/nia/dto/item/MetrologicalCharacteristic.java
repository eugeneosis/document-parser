package ru.fau.nia.dto.item;

import lombok.Data;
import ru.fau.nia.entity.DictOkei;

@Data
public class MetrologicalCharacteristic {
    private String from;
    private String to;
    private DictOkei uom;
    private String indicator;
    private String indicatorId;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (indicator != null) {
            builder.append(indicator);
        } else {
            if (from != null && !from.isEmpty()) {
                builder.append("от ").append(from).append(" ");
            }
            if (to != null && !to.isEmpty()) {
                builder.append("до ").append(to).append(" ");
            }
            if (uom != null && uom.getShortName() != null && !uom.getShortName().isEmpty()) {
                builder.append("(").append(uom.getShortName()).append(")");
            }
            if (builder.length() == 0) {
                builder.append("-");
            }
        }
        return builder.toString().trim();
    }
}
