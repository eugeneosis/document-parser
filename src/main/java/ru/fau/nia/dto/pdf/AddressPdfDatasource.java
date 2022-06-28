package ru.fau.nia.dto.pdf;

import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.dto.AccreditationAreas;
import ru.fau.nia.dto.AccreditationItem;
import ru.fau.nia.dto.AccreditationLine;
import ru.fau.nia.dto.Declaration;
import ru.fau.nia.entity.DictBusinessLineType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class AddressPdfDatasource {
    private String address;
    private String printedFormTemplate;
    private List<AccreditationAreaPdfDatasource> accreditationAreas;

    public AddressPdfDatasource(Declaration declaration, AccreditationLine accreditationLine, List<DictBusinessLineType> businessLineTypes) {
        this.address = accreditationLine.getAddress() == null ? null : accreditationLine.getAddress().getActualAddress();
        this.printedFormTemplate = accreditationLine.getPrintedFormTemplate();
        this.accreditationAreas = new ArrayList<>();
        String entityPrefix = "";

        Optional<DictBusinessLineType> optional = businessLineTypes.stream().findFirst();


    }

    private String setEntityPrefix(int id) {
        switch (id) {
            case 7:
                return "A";
            case 8:
                return "B";
            case 9:
                return "C";
        }
        return "";
    }

    public void addWithoutGroups(AccreditationLine accreditationLine) {
        accreditationLine.getAccreditationAreas().stream()
                .sorted(Comparator.comparing(areaa -> areaa.getBusinessLineType().getCode()))
                .forEach(area -> {
                    DictBusinessLineType businessLineType = area.getBusinessLineType();
                    AccreditationAreaPdfDatasource datasource = new AccreditationAreaPdfDatasource(
                            businessLineType.getValue(),
                            businessLineType,
                            area.getAccreditationItems()
                    );
                    datasource.setStigmaCipher(area.getStigmaCipher());
                    datasource.setPrefix(businessLineType.getCode() == null ? "" : businessLineType.getCode());
                    this.accreditationAreas.add(datasource);
                });
    }

    private boolean addMandatoryTypeGroup(String name, Integer id, String entityPrefix, String prefix,
                                          List<DictBusinessLineType> businessLineTypes,
                                          AccreditationLine accreditationLine) {

        List<Integer> typeIds = accreditationLine.getAccreditationAreas().stream()
                .map(area -> area.getBusinessLineType().getId())
                .collect(Collectors.toList());

        List<AccreditationAreaPdfDatasource> areas = businessLineTypes.stream()
                .filter(type -> id.equals(type.getId()) && typeIds.contains(type.getId()))
                .map(type -> {
                    Optional<AccreditationAreas> accreditationArea = accreditationLine.getAccreditationAreas().stream()
                            .filter(area -> area.getBusinessLineType().getId().equals(type.getId()))
                            .findFirst();

                    List<AccreditationItem> items = null;

                    if (accreditationArea.isPresent()) {
                        items = accreditationArea.get().getAccreditationItems();
                    }

                    return new AccreditationAreaPdfDatasource(type.getPrintValue(), type, items);
                })
                .collect(Collectors.toList());

        if (areas.stream().anyMatch(area -> area.getTable() != null)) {
            if (id == 1 || id == 2) {
                this.accreditationAreas.add(new AccreditationAreaPdfDatasource(name, prefix + "."));
            }
            areas.forEach(area -> {

                area.setPrefix(entityPrefix + "." + prefix + getPrefixByTypeId(area.getBusinessLineTypeId()) + ".");
                this.accreditationAreas.add(area);
            });
            return true;
        }
        return false;
    }

    private String getPrefixByTypeId(Integer id) {
        switch (id) {
            case 1:
            case 5:
                return "1";
            case 2:
            case 6:
                return "2";
            case 3:
            case 7:
                return "3";
            case 4:
            case 9:
                return "4";
        }
        return "0";
    }
}
