package ru.fau.nia.dto.pdf.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Collection;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
