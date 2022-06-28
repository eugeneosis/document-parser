package ru.fau.nia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeclarationInfo {

    private Integer id;

    private String accreditationType;

    private String accreditedEntityType;

    private String declarationTitle;

    private String declarationSubTitle;

    private String underscoreEntityTitle;
}
