package ru.fau.nia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableColumnHasRowField {

    private Integer id;

    private String name;

    @JsonIgnore
    private RowField rowField;


    @JsonIgnore
    private Integer tableColumnId;

    private String start;

    private String end;

    private String eachElementStart;

    private String eachElementEnd;

    private List<ObjectField> objectFields;

    public String getName() {
        return name == null ? rowField.getName() : name;
    }

    @JsonIgnore
    public String getPrintedFormDelimiter(String delimiter) {
        if ("validationScheme".equals(getName())) {
            return ", ";
        }

        if (delimiter == null) {
            return "";
        }
        return delimiter;
    }

    public String getPrintedFormValue(Object object) {
        String name;
        StringBuilder builder = new StringBuilder();
        Field field;
        boolean firstElement = true;
        if (objectFields != null && objectFields.size() > 0) {
            for (ObjectField objectField : objectFields) {
                try {
                    name = objectField.getName();
                    if ("name".equals(name)) {
                        name = "value";
                    }
                    try {
                        field = object.getClass().getDeclaredField(name);
                    } catch (NoSuchFieldException inEx) {
                        field = object.getClass().getSuperclass().getDeclaredField(name);
                    }
                    field.setAccessible(true);
                    if (!firstElement) {
                        builder.append(objectField.getDelimiter());
                    }
                    final var obj = field.get(object);
                    if (Objects.isNull(obj)) {
                        return null;
                    }
                    builder.append(obj);
                    firstElement = false;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            return builder.toString();
        }

        return null;
    }

    public String getStart() {
        if (start == null && "validationScheme".equals(getName())) {
            return "(";
        }
        return start;
    }

    public String getEnd() {
        if (end == null && "validationScheme".equals(getName())) {
            return ")";
        }
        return end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowField.getId(), tableColumnId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TableColumnHasRowField)) {
            return false;
        }
        TableColumnHasRowField tc = (TableColumnHasRowField) obj;

        return Objects.equals(tc.rowField.getId(), rowField.getId()) && Objects.equals(tc.tableColumnId, tableColumnId);
    }
}
