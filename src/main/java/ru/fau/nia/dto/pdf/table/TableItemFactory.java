package ru.fau.nia.dto.pdf.table;

import ru.fau.nia.dto.item.Item;

import java.util.Collection;
import java.util.stream.Collectors;

public class TableItemFactory {

    public Collection<TableItem> createItemDividedIntoColumns(Collection<Item> itemCollection) {
        return itemCollection
                .stream()
                .map(ItemDividedIntoColumns::new)
                .collect(Collectors.toList());
    }
}
