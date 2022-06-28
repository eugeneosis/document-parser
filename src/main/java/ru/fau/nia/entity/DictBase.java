package ru.fau.nia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface DictBase extends EntityId {

    @JsonIgnore
    Object getIdDict();

    String getValue();

    default boolean isDuplicate(DictBase dictBase) {
        return false;
    }
}
