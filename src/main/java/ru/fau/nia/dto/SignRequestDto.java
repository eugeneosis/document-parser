package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignRequestDto {
    private List<String> fileIds;
    private String returnUrl;
    private String clientSigningMode;
}
