package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedFormValue {
    private SelectedTnvedCode tnvedCode;
    private SelectedDocType docType;
    private SelectedNormDoc normDoc;
    private Object productName;
    private SelectedNode note;
}
