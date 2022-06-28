package ru.fau.nia.dto.pdf.table;

import ru.fau.nia.dto.item.Item;

public class ItemDividedIntoColumns implements TableItem {
    private Item item;

    public ItemDividedIntoColumns(Item item) {
        this.item = item;
    }

    @Override
    public TableItem getColumn1Value() {
        return new TableCell(item.getColumn1());
    }

    @Override
    public TableItem getColumn2Value() {
        return new TableCell(item.getColumn2());
    }
}
