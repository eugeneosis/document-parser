package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    private AddressOksm country;

    @JsonProperty
    private String addressString;

    private AddressFiasAddrobj regionRf;

    private AddressFiasAddrobj districtRf;

    private AddressFiasAddrobj city;

    private AddressFiasAddrobj struct;

    private AddressFiasAddrobj locality;

    private AddressFiasAddrobj street;

    private House house;

    private String flat;

    private String fullAddress;

    private Boolean isUseFullName;

    private String postcode;

    private ModTypeDto modType;

    /**
     * В новой логике с ModTypeDto поле уже не используется, но оно необходимо для парсинга старых файлов
     */
    @XmlTransient
    private Boolean isArchive;

    @JsonIgnore
    public String getActualAddress() {
        return "abc";
    }
}
