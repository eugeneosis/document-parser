package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.dto.item.Item;
import ru.fau.nia.dto.pdf.table.TableItem;
import ru.fau.nia.entity.DictBusinessLineType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccreditationItem implements TableItem {

    private Integer indexNumber;

    private Collection<Item> items;

    @JsonIgnore
    private DictBusinessLineType businessLineType;

    public Collection<Object> getItemIds(String name, Object defaultValue) {
        List<Object> result = items.stream()
                .filter(item -> item.getDictionaryId() != null && item.getName().equals(name))
                .map(Item::getDictionaryId)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            result.add(defaultValue);
        }
        return result;
    }

    public Object getItemId(String name) {
        Object result = items.stream()
                .filter(item -> item.getDictionaryId() != null && item.getName().equals(name))
                .map(Item::getDictionaryId)
                .findAny()
                .orElse(null);
        if (result == null) {
            throw new RuntimeException(String.format("There is no a single element with name %s", name));
        }
        return result;
    }

    public String getUniqueKeyOfItems(String name) {
        return items.stream()
                .filter(item -> item.getDictionaryId() != null && item.getName().equals(name))
                .map(i -> i.getName() + "/" + i.getDictionaryId())
                .sorted()
                .collect(Collectors.joining(","));
    }

    @JsonIgnore
    @Override
    public Collection<TableItem> getRows() {
        throw new RuntimeException(getClass().getName() + " do not have any rows");
    }

    @JsonIgnore
    @Override
    public String getRowNumber() {
        return businessLineType.getCode() + indexNumber + ".";
    }

    @JsonIgnore
    @Override
    public String getBusinessLineTypeValue() {
        return businessLineType.getValue();
    }

    @JsonIgnore
    @Override
    public TableItem getColumn1Value() {
        return businessLineType.getColumnValue(0, items);
    }

    @JsonIgnore
    @Override
    public TableItem getColumn2Value() {
        return businessLineType.getColumnValue(1, items);
    }

    @JsonIgnore
    @Override
    public TableItem getColumn3Value() {
        return businessLineType.getColumnValue(2, items);
    }

    @JsonIgnore
    @Override
    public TableItem getColumn4Value() {
        return businessLineType.getColumnValue(3, items);
    }

    @JsonIgnore
    @Override
    public TableItem getColumn5Value() {
        return businessLineType.getColumnValue(4, items);
    }

    @JsonIgnore
    @Override
    public TableItem getColumn6Value() {
        return businessLineType.getColumnValue(5, items);
    }

    @JsonIgnore
    @Override
    public TableItem getColumn7Value() {
        return businessLineType.getColumnValue(6, items);
    }

    public void accept(DeclarationVisitor visitor) {
        visitor.visit(this);
        items.forEach(visitor::visit);
    }
}
