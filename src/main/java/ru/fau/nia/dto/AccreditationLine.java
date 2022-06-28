package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccreditationLine implements Cloneable {
    @JsonProperty("address")
    private Address address;

    @JacksonXmlElementWrapper(localName = "accreditationAreas")
    @JacksonXmlProperty(localName = "accreditationArea")
    @JsonProperty("accreditationAreas")
    private List<AccreditationAreas> accreditationAreas = new ArrayList<>();

    public void setUpForPdfExport() {
        for (AccreditationAreas accreditationArea : accreditationAreas) {
            accreditationArea.setUpForPdfExport();
        }
    }

    @JsonIgnore
    public Collection<AccreditationLine> split() {
        accreditationAreas.sort(Comparator.comparing(AccreditationAreas::getCode));

        Map<String, AccreditationLine> map = new LinkedHashMap<>();
        String printedFormTemplate;
        for (AccreditationAreas accreditationArea : accreditationAreas) {
            printedFormTemplate = accreditationArea.getCode();
            AccreditationLine accreditationLine = map.get(printedFormTemplate);
            if (accreditationLine == null) {
                try {
                    accreditationLine = (AccreditationLine) this.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                accreditationLine.accreditationAreas = new ArrayList<>();
                accreditationLine.accreditationAreas.add(accreditationArea);
                map.put(printedFormTemplate, accreditationLine);
            } else {
                accreditationLine.getAccreditationAreas().add(accreditationArea);
            }
        }

        List<AccreditationLine> list = new ArrayList<>(map.values());
        int i = 0;
        for (AccreditationLine line : list) {
            if (i > 0) {
                line.setAddress(null);
            }
            i++;
        }
        return list;
    }

    @JsonIgnore
    public String getPrintedFormTemplate() {
        String result = null;
        String current;
        for (AccreditationAreas accreditationArea : accreditationAreas) {
            current = accreditationArea.getCode();
            if (result == null) {
                result = current;
            }
            if (!result.equals(current)) {
                throw new RuntimeException(
                        "Невозможно сформировать одну таблицу печатной формы так как для разных направлений деятельности таблицы разные");
            }
        }

        return result;
    }

    public void accept(DeclarationVisitor visitor) {
        visitor.visit(this);
        visitor.visit(address);
        accreditationAreas.forEach(o -> o.accept(visitor));
    }
}
