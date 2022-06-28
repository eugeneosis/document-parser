package ru.fau.nia.dto.pdf.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.fau.nia.dto.item.Item;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableItemFactory {

    public Collection<TableItem> createItemDividedIntoColumns(Collection<Item> itemCollection) {
        return itemCollection
                .stream()
                .map(ItemDividedIntoColumns::new)
                .collect(Collectors.toList());
    }
}
