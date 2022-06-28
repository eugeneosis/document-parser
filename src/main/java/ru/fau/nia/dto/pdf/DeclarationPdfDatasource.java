package ru.fau.nia.dto.pdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.dto.AccreditationLine;
import ru.fau.nia.dto.Declaration;
import ru.fau.nia.entity.DictBusinessLineType;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeclarationPdfDatasource {
    private String accreditedEntityType;
    private String firstPartOfAccreditedEntityTypeName;
    private String secondPartOfAccreditedEntityTypeName;
    private String underScoreAccreditedEntityTypeName;
    private String accreditedEntityName;
    private String certificationNumber;
    private Integer accreditationTypeId;
    private List<String> addresses;
    private List<AddressPdfDatasource> lines;
    private static final String ACCREDITATION_AREA = "ОБЛАСТЬ АККРЕДИТАЦИИ";


    public DeclarationPdfDatasource(Declaration declaration, List<DictBusinessLineType> businessLineTypes) {
        this.accreditedEntityName = declaration.getAccreditedEntityName();
        if (declaration.getCertificateNumber() != null) {
            this.certificationNumber = declaration.getCertificateNumber().getValue();
        }

        lines = new ArrayList<>();
        for (AccreditationLine line1 : declaration.getAccreditationLines()) {
            for (AccreditationLine line2 : line1.split()) {
                lines.add(new AddressPdfDatasource(declaration, line2, businessLineTypes));
            }
        }
    }

    private String getPDFDebug() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("accreditedEntityType: ").append(accreditedEntityType).append("\r\n");
        stringBuilder.append("firstPartOfAccreditedEntityTypeName: ").append(firstPartOfAccreditedEntityTypeName).append("\r\n");
        stringBuilder.append("secondPartOfAccreditedEntityTypeName: ").append(secondPartOfAccreditedEntityTypeName).append("\r\n");
        stringBuilder.append("underScoreAccreditedEntityTypeName: ").append(underScoreAccreditedEntityTypeName).append("\r\n");
        stringBuilder.append("accreditationTypeId: ").append(accreditationTypeId).append("\r\n");
        stringBuilder.append("accreditedEntityName: ").append(accreditedEntityName).append("\r\n");

        String addresses_ = addresses.stream()
                .map(addr -> "address: " + addr)
                .collect(Collectors.joining("\r\n"));
        stringBuilder.append(addresses_);

        for (AddressPdfDatasource addrPdfDataSource : lines) {
            for (AccreditationAreaPdfDatasource accreditationAreaPdfDatasource : addrPdfDataSource.getAccreditationAreas()) {
                stringBuilder.append("BusinessLineType: ").append(accreditationAreaPdfDatasource.getBusinessLineType()).append("\r\n");
                stringBuilder.append("BusinessLineTypeId: ").append(accreditationAreaPdfDatasource.getBusinessLineTypeId()).append("\r\n");
                stringBuilder.append("IsGroup: ").append(accreditationAreaPdfDatasource.getIsGroup()).append("\r\n");
                stringBuilder.append("Prefix: ").append(accreditationAreaPdfDatasource.getPrefix()).append("\r\n");
                stringBuilder.append("Items.......").append("\r\n");
            }
        }

        return stringBuilder.toString();
    }
}
