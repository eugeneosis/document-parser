package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedParentMethod {
    private Integer id;
    private String name;
    private Boolean forNewLine;
}
