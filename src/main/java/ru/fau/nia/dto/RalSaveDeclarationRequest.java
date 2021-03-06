package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RalSaveDeclarationRequest {
    private Integer ralId;
    private Integer operationTypeId;
    private Integer operationId;
    private Declaration declaration;

    public RalSaveDeclarationRequest(Integer ralId, Integer operationTypeId, Integer operationId, Declaration declaration) {
        this.ralId = ralId;
        this.operationTypeId = operationTypeId;
        this.operationId = operationId;
        this.declaration = declaration;
    }
}
