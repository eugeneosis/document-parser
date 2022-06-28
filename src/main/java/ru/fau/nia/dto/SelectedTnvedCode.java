package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.entity.DictTnved;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedTnvedCode {
    private List<DictTnved> value;
}
