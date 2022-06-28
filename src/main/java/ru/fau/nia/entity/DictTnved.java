package ru.fau.nia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictTnved {

    private Integer id;

    private String value;

    private String code;

    private boolean isAnyChildren;

    private Integer idParent;
}
