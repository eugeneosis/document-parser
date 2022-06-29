package ru.fau.nia.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import ru.fau.nia.entity.DictNormDocType;

import java.util.Date;

@Data
@JacksonXmlRootElement(localName = "certificateNumber")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryDto {
    private Integer id;

    @JacksonXmlProperty(localName = "name")
    private String value;
    private String code;
    private boolean isAnyChildren;
    private Integer idParent;
    private Integer validationFormCode;
    private String dict;

    private Date startDate;
    private Date endDate;
    private Date expiryDate;
    private Boolean isMethods;
    private Boolean isRequirements;

    private DictNormDocType normDocType;
    private Integer docStatus;
    private Integer docType;
    private String adoptedBy;
    private String useInsteadOf;
    private String sectionNumber;
    private boolean editable;
    private boolean anyChildren;

    private boolean custom;
    private boolean hidden;

    @JacksonXmlProperty(localName = "addedManually")
    private boolean isAddedManually;

    @JacksonXmlProperty(localName = "isMandatory")
    private boolean isMandatory;

    @JsonIgnore
    public Integer getDocType() {
        if (normDocType != null) {
            return normDocType.getId();
        }
        return docType;
    }
}
