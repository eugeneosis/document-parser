package ru.fau.nia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RowFieldFilter {

    private Integer id;

    private String filterName;

    private Boolean isRadioButton;

    private String additionalFilterName;

    private String additionalFilterDictionary;

    private String additionalFilterCode;

    private String filterCode;
}
