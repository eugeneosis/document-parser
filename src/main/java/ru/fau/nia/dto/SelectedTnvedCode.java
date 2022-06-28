package ru.fau.nia.dto;

import lombok.Data;
import ru.fau.nia.entity.DictTnved;

import java.util.List;

@Data
public class SelectedTnvedCode {
    private List<DictTnved> value;
}
