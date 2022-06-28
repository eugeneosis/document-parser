package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ColumnDivided {
    @JsonIgnore
    String getColumn1();

    @JsonIgnore
    String getColumn2();
}
