package ru.fau.nia.dto.pdf.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.dto.AccreditationItem;
import ru.fau.nia.dto.item.Item;
import ru.fau.nia.entity.DictBusinessLineType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CellCombiningRow implements TableItem {
    private Integer columnNumber;
    private Collection<AccreditationItem> accreditationItems;
    private DictBusinessLineType businessLineType;
    private List<CellCombiningInstruction> cellCombiningInstructions;
    private Integer rowNumber;
    private AccreditationItem accreditationItem;
    private Collection<Item> itemCollection;

    public CellCombiningRow(Integer columnNumber, Collection<AccreditationItem> accreditationItems,
                            DictBusinessLineType businessLineType,
                            List<CellCombiningInstruction> cellCombiningInstructions, Integer rowNumber) {
        this.columnNumber = columnNumber;
        this.accreditationItems = accreditationItems;
        this.businessLineType = businessLineType;
        this.cellCombiningInstructions = cellCombiningInstructions;
        this.rowNumber = rowNumber;

        accreditationItem = accreditationItems.stream().findFirst().orElseGet(null);
        itemCollection = accreditationItem == null ? new ArrayList<>() : accreditationItem.getItems();
    }

    @Override
    public String getRowNumber() {
        return rowNumber.toString();
    }

    @Override
    public TableItem getColumn1Value() {
        return getValue(0);
    }

    @Override
    public TableItem getColumn2Value() {
        return getValue(1);
    }

    @Override
    public TableItem getColumn3Value() {
        return getValue(2);
    }

    @Override
    public TableItem getColumn4Value() {
        return getValue(3);
    }

    @Override
    public TableItem getColumn5Value() {
        return getValue(4);
    }

    @Override
    public TableItem getColumn6Value() {
        return getValue(5);
    }

    @Override
    public TableItem getColumn7Value() {
        return getValue(6);
    }

    private TableItem getValue(int currentColumnNumber) {
        CellCombiningInstruction instruction = cellCombiningInstructions.get(columnNumber + currentColumnNumber);
        if (currentColumnNumber > 0 && instruction.isCombineColumn()) {
            return new CellCombiningTable(
                    columnNumber + currentColumnNumber,
                    accreditationItems,
                    businessLineType,
                    cellCombiningInstructions
            );
        }

        return businessLineType.getColumnValue(columnNumber + currentColumnNumber, itemCollection);
    }

    public AccreditationItem getAccreditationItem() {
        return accreditationItem;
    }
}
