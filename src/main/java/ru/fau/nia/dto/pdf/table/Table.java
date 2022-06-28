package ru.fau.nia.dto.pdf.table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.entity.DictBusinessLineType;

import java.util.Collection;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Table implements TableItem {
    private DictBusinessLineType businessLineType;
    private Collection<? extends TableItem> collection;

    public Table(DictBusinessLineType businessLineType, Collection<? extends TableItem> collection) {
        this.businessLineType = businessLineType;
        this.collection = collection;
    }

    @Override
    public Collection<TableItem> getRows() {
        return (Collection<TableItem>) collection;
    }

    @Override
    public String getPrefix() {
        return businessLineType.getCode();
    }
}
