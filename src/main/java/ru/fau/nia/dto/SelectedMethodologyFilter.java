package ru.fau.nia.dto;

import lombok.Data;
import ru.fau.nia.entity.DictNormDocType;
import ru.fau.nia.entity.RowFieldFilter;

@Data
public class SelectedMethodologyFilter {
    private RowFieldFilter radioFilter;
    private DictNormDocType additionalFilter;
}
