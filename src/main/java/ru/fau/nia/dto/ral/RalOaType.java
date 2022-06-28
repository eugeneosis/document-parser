package ru.fau.nia.dto.ral;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class RalOaType {
    private Integer id;
    private String name;

    private String accreditedEntityName;
    private String certificateNumber;
    private String declarantName;
    private String declarantTaxNumber;

    private Collection<RalAddress> addresses = new ArrayList<>();
}
