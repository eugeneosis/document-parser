package ru.fau.nia.dto.ral;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class RalBusinessLineType {
    private Integer id;

    private String name;

    private Collection<RalField> fields = new ArrayList<>();

    public RalBusinessLineType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
