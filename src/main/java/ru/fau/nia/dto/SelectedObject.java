package ru.fau.nia.dto;

import lombok.Data;

@Data
public class SelectedObject {
    private Integer id;
    private String name;
    private String code;
    private Boolean selected;
    private SelectedFormValue formValues;
    private String dict;
}
