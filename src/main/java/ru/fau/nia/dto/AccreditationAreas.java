package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import ru.fau.nia.entity.DictBusinessLineType;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccreditationAreas {

    @JacksonXmlProperty(localName = "businessLineType")
    @JsonProperty("businessLineType")
    private DictBusinessLineType businessLineType;

    @JsonProperty("stigmaCipher")
    private String stigmaCipher;

    @JsonProperty("measuringTechniquesCertification")
    private String measuringTechniquesCertification;

    @JsonProperty("metrologicalExamination")
    private String metrologicalExamination;

    @JacksonXmlElementWrapper(localName = "accreditationItems")
    @JacksonXmlProperty(localName = "accreditationItem")
    @JsonProperty("accreditationItems")
    private List<AccreditationItem> accreditationItems = new ArrayList<>();

    private Selected selected;

    public void setUpForPdfExport() {
        for (AccreditationItem accreditationItem : accreditationItems) {
            if (CollectionUtils.isEmpty(accreditationItem.getItems())) {
                throw new RuntimeException(
                        String.format("Не заполнены обязательные поля в направлении '%s'",
                                businessLineType.getValue()));
            }
            accreditationItem.setBusinessLineType(businessLineType);
        }
    }

    @JsonIgnore
    public String getCode() {
        return businessLineType.getCode();
    }

    public void accept(DeclarationVisitor visitor) {
        visitor.visit(this);
        visitor.visit(businessLineType);
        accreditationItems.forEach(o -> o.accept(visitor));
    }

    @JsonIgnore
    public Integer getBusinessLineTypeId() {
        return businessLineType == null ? null : businessLineType.getId();
    }
}
