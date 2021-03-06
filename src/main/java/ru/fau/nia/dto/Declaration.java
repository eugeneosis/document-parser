package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.dto.item.DictionaryDto;
import ru.fau.nia.dto.item.Item;
import ru.fau.nia.entity.DeclarationInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@JacksonXmlRootElement(localName = "declaration")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Declaration {

    @JacksonXmlProperty(localName = "version")
    private int version = 1;

    @JacksonXmlProperty(localName = "accreditedEntityName")
    private String accreditedEntityName;

    @JacksonXmlProperty(localName = "certificateNumber")
    //@JsonDeserialize(using = RegNumDeserializer.class)
    private DictionaryDto certificateNumber;

    @JacksonXmlProperty(localName = "declarantName")
    private String declarantName;

    @JacksonXmlProperty(localName = "declarantTaxNumber")
    private String declarantTaxNumber;

    //@JacksonXmlProperty(localName = "declarationInfo")
    //private DeclarationInfo declarationInfo;

    @JacksonXmlElementWrapper(localName = "accreditationLines")
    @JacksonXmlProperty(localName = "accreditationLine")
    private Collection<AccreditationLine> accreditationLines = new ArrayList<>();

    @JacksonXmlProperty(localName = "lastChangeDate")
    private LocalDate lastChangeDate;

    @JacksonXmlProperty(localName = "manuallyAddedDocuments")
    private Collection<DictionaryDto> manuallyAddedDocuments;

    @JsonIgnore
    public void setUpForPdfExport() {
        for (AccreditationLine accreditationLine : accreditationLines) {
            accreditationLine.setUpForPdfExport();
        }
    }

  /*  @JsonIgnore
    public void accept(DeclarationVisitor visitor) {
        visitor.visit(this);
        visitor.visit((Item) declarationInfo);
        accreditationLines.forEach(o -> o.accept(visitor));
    }*/

    public static class RegNumDeserializer extends JsonDeserializer<DictionaryDto> {

        @Override
        public DictionaryDto deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String t = parser.getText();

            JsonNode node = parser.getCodec().readTree(parser);
            if (node.get("id") != null) {
                int id = node.get("id").asInt();
                String name = node.get("name").asText();

                DictionaryDto dd = new DictionaryDto();
                dd.setId(id);
                dd.setValue(name);
                return dd;
            }

            if (t == null) {
                return null;
            }

            DictionaryDto dd = new DictionaryDto();
            dd.setId(1);
            dd.setValue(t);
            return dd;
        }
    }
}
