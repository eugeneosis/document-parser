package ru.fau.nia.dto.pdf.table;

import lombok.Getter;

import java.util.Comparator;

@Getter
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
