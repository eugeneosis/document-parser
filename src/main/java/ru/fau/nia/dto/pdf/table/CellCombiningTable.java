package ru.fau.nia.dto.pdf.table;

import ru.fau.nia.dto.AccreditationItem;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public Collection<TableItem> getRows() {
        int[] i = {0};
        return businessLineType.getGroupedByColumn(columnNumber, accreditationItems)
                .values()
                .stream()
                .map(items -> new CellCombiningRow(
                        columnNumber,
                        items,
                        businessLineType,
                        cellCombiningInstructions,
                        ++i[0]))
                .sorted(cellCombiningInstructions.get(columnNumber).getComparator())
                .collect(Collectors.toList());
    }

    @Override
    public String getPrefix() {
        return businessLineType.getCode();
    }
}
