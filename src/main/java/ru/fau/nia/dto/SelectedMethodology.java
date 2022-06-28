package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.entity.DictNormDocType;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedMethodology {
    private Integer id;
    private String name;
    private String code;
    private Date endDate;
    private Date startDate;
    private Date expiryDate;
    private Boolean isMethods;
    private Boolean isRequirements;
    private DictNormDocType normDocType;
    private Integer docStatus;
    private Integer docType;
    private Boolean anyChildren;
    private String adoptedBy;
    private String useInsteadOf;
    private Boolean selected;
    private Boolean forNewLine;
    private String sectionNumber;
    private boolean addedManually;
    private List<SelectedMethod> methods;
}
