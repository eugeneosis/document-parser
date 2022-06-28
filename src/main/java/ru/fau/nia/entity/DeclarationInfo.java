package ru.fau.nia.entity;

import lombok.Data;

@Data
public class DeclarationInfo {

    private Integer id;

    private String accreditationType;

    private String accreditedEntityType;

    private String declarationTitle;

    private String declarationSubTitle;

    private String underscoreEntityTitle;

}
