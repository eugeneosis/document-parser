package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.entity.DictOkpd2;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedProductName {
    private List<DictOkpd2> value;
}
