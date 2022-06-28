package ru.fau.nia.dto;

import lombok.Data;
import ru.parma.fgis.oa.data.entity.conf.RowFieldFilter;
import ru.parma.fgis.oa.dictionary.entity.DictNormDocType;

@Data
public class SelectedMethodologyFilter {
    private RowFieldFilter radioFilter;
    private DictNormDocType additionalFilter;
}
