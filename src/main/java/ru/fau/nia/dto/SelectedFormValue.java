package ru.fau.nia.dto;

import lombok.Data;

@Data
public class SelectedFormValue {
    private SelectedTnvedCode tnvedCode;
    private SelectedDocType docType;
    private SelectedNormDoc normDoc;
    private Object productName;
    private SelectedNode note;
}
