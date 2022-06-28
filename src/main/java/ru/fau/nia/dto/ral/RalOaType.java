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
public class RalOaType {
    private Integer id;
    private String name;

    private String accreditedEntityName;
    private String certificateNumber;
    private String declarantName;
    private String declarantTaxNumber;

    private Collection<RalAddress> addresses = new ArrayList<>();
}
