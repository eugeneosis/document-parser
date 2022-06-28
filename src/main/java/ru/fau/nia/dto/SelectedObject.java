package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedObject {
    private Integer id;
    private String name;
    private String code;
    private Boolean selected;
    private SelectedFormValue formValues;
    private String dict;
}
