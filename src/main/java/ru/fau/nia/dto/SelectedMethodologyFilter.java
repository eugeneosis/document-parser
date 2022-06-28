package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.entity.DictNormDocType;
import ru.fau.nia.entity.RowFieldFilter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedMethodologyFilter {
    private RowFieldFilter radioFilter;
    private DictNormDocType additionalFilter;
}
