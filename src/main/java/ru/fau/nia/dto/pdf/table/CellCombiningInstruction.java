package ru.fau.nia.dto.pdf.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import java.util.Comparator;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CellCombiningInstruction {
    private boolean isCombineColumn;

    private Comparator<CellCombiningRow> comparator;

    public CellCombiningInstruction(boolean isCombineColumn) {
        this.isCombineColumn = isCombineColumn;
        this.comparator = new DefaultComparator<>();
    }

    public CellCombiningInstruction(boolean isCombineColumn, Comparator<CellCombiningRow> comparator) {
        this.isCombineColumn = isCombineColumn;
        this.comparator = comparator;
    }

    private static class DefaultComparator<CellCombiningRow> implements Comparator<CellCombiningRow> {
        @Override
        public int compare(CellCombiningRow o1, CellCombiningRow o2) {
            return 0;
        }
    }
}
