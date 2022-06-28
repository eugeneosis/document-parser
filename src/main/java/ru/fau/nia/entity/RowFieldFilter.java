package ru.fau.nia.entity;

import lombok.Data;

@Data
public class RowFieldFilter {

    private Integer id;

    private String filterName;

    private Boolean isRadioButton;

    private String additionalFilterName;

    private String additionalFilterDictionary;

    private String additionalFilterCode;

    private String filterCode;
}
