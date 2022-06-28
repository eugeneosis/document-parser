package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.entity.DictNormDocType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedDocType {
    private DictNormDocType value;
}
