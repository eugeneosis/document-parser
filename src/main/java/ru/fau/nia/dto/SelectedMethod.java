package ru.fau.nia.dto;

import lombok.Data;
import ru.fau.nia.dto.item.Range;
import java.util.List;

@Data
public class SelectedMethod {
    private Integer id;
    private String name;
    private Boolean forNewLine;
    private SelectedParentMethod parentMethod;
    private Boolean selected;
    private List<Range> measurements;
}
