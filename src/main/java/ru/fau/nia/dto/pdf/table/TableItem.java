package ru.fau.nia.dto.pdf.table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;

public interface TableItem {
    default Collection<TableItem> getRows() {
        throw new RuntimeException(getClass().getName() + " do not have rows");
    }

    @JsonIgnore
    default String getPrefix() {
        throw new RuntimeException(getClass().getName() + " do not have prefix");
    }

    @JsonIgnore
    default String getRowNumber() {
        throw new RuntimeException(getClass().getName() + " do not have row number");
    }

    @JsonIgnore
    default String getBusinessLineTypeValue() {
        throw new RuntimeException(getClass().getName() + " do not have businessLineTypeValue");
    }

    @JsonIgnore
    default TableItem getColumn1Value() {
        throw new RuntimeException(getClass().getName() + " do not have column1");
    }

    @JsonIgnore
    default TableItem getColumn2Value() {
        throw new RuntimeException(getClass().getName() + " do not have column2");
    }

    @JsonIgnore
    default TableItem getColumn3Value() {
        throw new RuntimeException(getClass().getName() + " do not have column3");
    }

    @JsonIgnore
    default TableItem getColumn4Value() {
        throw new RuntimeException(getClass().getName() + " do not have column4");
    }

    @JsonIgnore
    default TableItem getColumn5Value() {
        throw new RuntimeException(getClass().getName() + " do not have column5");
    }

    @JsonIgnore
    default TableItem getColumn6Value() {
        throw new RuntimeException(getClass().getName() + " do not have column6");
    }

    @JsonIgnore
    default TableItem getColumn7Value() {
        throw new RuntimeException(getClass().getName() + " do not have column6");
    }

    String toString();
}
