package ru.fau.nia.dto.pdf.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.dto.AccreditationItem;
import ru.fau.nia.entity.DictBusinessLineType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CellCombiningTable implements TableItem {
    private Integer columnNumber;
    private Collection<AccreditationItem> accreditationItems;
    private DictBusinessLineType businessLineType;
    private List<CellCombiningInstruction> cellCombiningInstructions;

    public CellCombiningTable(Integer columnNumber, Collection<AccreditationItem> accreditationItems,
                              DictBusinessLineType businessLineType,
                              List<CellCombiningInstruction> cellCombiningInstructions) {
        this.columnNumber = columnNumber;
        this.accreditationItems = accreditationItems;
        this.businessLineType = businessLineType;
        this.cellCombiningInstructions = cellCombiningInstructions;
    }

    @Override
    public String getPrefix() {
        return businessLineType.getCode();
    }
}
