package ru.fau.nia.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class SignRequestDto {
    private List<String> fileIds;
    private String returnUrl;
    private String clientSigningMode;
}
