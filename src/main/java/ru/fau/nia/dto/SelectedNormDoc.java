package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.entity.DictNormDoc;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedNormDoc {
    private DictNormDoc value;
}
