package ru.fau.nia.dto.pdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.dto.AccreditationItem;
import ru.fau.nia.dto.pdf.table.CellCombiningRow;
import ru.fau.nia.dto.pdf.table.Table;
import ru.fau.nia.dto.pdf.table.TableItem;
import ru.fau.nia.entity.DictBusinessLineType;

import java.util.*;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccreditationAreaPdfDatasource {
    private String prefix;
    private Boolean isGroup = false;
    private Integer businessLineTypeId;
    private String businessLineType;
    private String stigmaCipher;
    private TableItem table;

    public AccreditationAreaPdfDatasource(String name, DictBusinessLineType businessLineType, List<AccreditationItem> items) {
        this.businessLineType = name;
        this.businessLineTypeId = businessLineType.getId();
        this.table = new Table(businessLineType, items);
    }

    public AccreditationAreaPdfDatasource(String name, String prefix) {
        this.businessLineType = name;
        this.prefix = prefix;
        this.isGroup = true;
        this.table = new TableItem() {
            @Override
            public Collection<TableItem> getRows() {
                return new ArrayList<>();
            }

            @Override
            public String getPrefix() {
                return prefix;
            }
        };
    }

    private static class pmciSecondStepComparator implements Comparator<CellCombiningRow> {
        private static final Map<String, Integer> map;

        static {
            map = new HashMap<>();
            map.put("okpd2Code", 1);
            map.put("environmentObject", 2);
            map.put("workEnvironment", 3);
            map.put("biologicalMaterial", 4);
        }

        @Override
        public int compare(CellCombiningRow o1, CellCombiningRow o2) {
            return getWeight(o1.getAccreditationItem()).compareTo(getWeight(o2.getAccreditationItem()));
        }

        private Integer getWeight(AccreditationItem accreditationItem) {
            if (accreditationItem == null) {
                return 0;
            }
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                try {
                    accreditationItem.getItemId(entry.getKey());
                    return entry.getValue();
                } catch (RuntimeException ignored) {

                }
            }
            return 0;
        }
    }
}
