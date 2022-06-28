package ru.fau.nia.entity;

import lombok.Data;
import ru.fau.nia.dto.item.Item;
import ru.fau.nia.dto.pdf.table.TableCell;
import ru.fau.nia.dto.pdf.table.TableItem;

import java.util.Collection;

@Data
public class DictBusinessLineType {

    private Integer id;

    private String value;

    private String printValue;

    private String description;

    private String shortName;

    private String code;

    private Boolean isActive;

    private String tableTitle;

    public TableItem getColumnValue(int columnNumber, Collection<Item> items) {
        return new TableCell("");
    }
}