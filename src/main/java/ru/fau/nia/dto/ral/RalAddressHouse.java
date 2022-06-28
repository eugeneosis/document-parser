package ru.fau.nia.dto.ral;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RalAddressHouse {

    private String id;

    private String name;

    private String postcode;
}
