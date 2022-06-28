package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModTypeDto {

    private Integer id;
    private String name;
    private boolean visible;

    public String getNameWithBrackets() {
        if (StringUtils.isNotBlank(name)) {
            return "(" + name + ")";
        }
        return "";
    }
}
