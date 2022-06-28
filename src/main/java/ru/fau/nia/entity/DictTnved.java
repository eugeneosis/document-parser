package ru.fau.nia.entity;

import lombok.Data;

@Data
public class DictTnved {

    private Integer id;

    private String value;

    private String code;

    private boolean isAnyChildren;

    private Integer idParent;
}
