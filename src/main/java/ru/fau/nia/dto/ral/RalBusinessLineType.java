package ru.fau.nia.dto.ral;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RalBusinessLineType {
    private Integer id;

    private String name;

    private Collection<RalField> fields = new ArrayList<>();

    public RalBusinessLineType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
