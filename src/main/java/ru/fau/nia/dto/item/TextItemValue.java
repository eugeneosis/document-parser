package ru.fau.nia.dto.item;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.fau.nia.entity.TableColumnHasRowField;

import java.io.IOException;

@Setter
@Getter
@EqualsAndHashCode
public class TextItemValue implements ItemValue {
    @JsonDeserialize(using = StringDeserializer.class)
    private String value;

    @Override
    public Type getType() {
        return Type.text;
    }

    @Override
    public Object getDictionaryId() {
        return value;
    }

    @Override
    public String getPrintedFormValue(TableColumnHasRowField rowField, Integer accreditationBodyTypeId, int columnNumber) {
        return value;
    }

    @Override
    public String getStringValue() {
        return value;
    }

    @Override
    public String getDictValue() {
        return null;
    }
}

class StringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return jsonParser.readValueAs(String.class);
        } catch (Exception e) {
            jsonParser.skipChildren();
            return "";
        }
    }
}
