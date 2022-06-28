package ru.fau.nia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Selected {
    private SelectedMethodologyFilter methodologyFilter;
    private List<SelectedMethodology> pickedMethodologies;
    private List<SelectedObject> pickedObjects;
}
