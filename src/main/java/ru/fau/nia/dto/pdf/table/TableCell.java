package ru.fau.nia.dto.pdf.table;

import java.util.Collection;

public class TableCell implements TableItem {
    private String cellValue;

    public TableCell(String cellValue) {
        this.cellValue = cellValue;
    }

    @Override
    public Collection<TableItem> getRows() {
        throw new RuntimeException("Table cell do not have rows");
    }

    @Override
    public String toString() {
        return cellValue;
    }
}
