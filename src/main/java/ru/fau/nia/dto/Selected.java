package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Selected {
    private SelectedMethodologyFilter methodologyFilter;
    private List<SelectedMethodology> pickedMethodologies;
    private List<SelectedObject> pickedObjects;
}
