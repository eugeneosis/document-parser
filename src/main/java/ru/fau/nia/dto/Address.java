package ru.fau.nia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Data
@EqualsAndHashCode(exclude = "isArchive")
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
        AddressBuilder builder = new AddressBuilder();
        if (country != null) {
            builder.setCountry(country.getShortName());
            if (isUseFullName == null && country.getShortName() == null) {
                builder.setCountry(country.getValue());
            }
            if (isUseFullName != null && isUseFullName) {
                builder.setCountry(country.getValue());
            }
        }
        if (regionRf != null) {
            builder.setRegion(regionRf.getValue());
        }
        if (districtRf != null) {
            builder.setDistrict(districtRf.getValue());
        }
        if (city != null) {
            builder.setCity(city.getValue());
        }
        if (struct != null) {
            builder.setStruct(struct.getValue());
        }
        if (locality != null) {
            builder.setLocality(locality.getValue());
        }
        if (street != null) {
            builder.setStreet(street.getValue());
        }
        if (house != null) {
            builder.setHouse(house.getName());
            builder.setPostcode(postcode != null && !postcode.equals(house.getPostcode()) ? postcode : house.getPostcode());
        } else {
            if (StringUtils.isNotBlank(postcode)) {
                builder.setPostcode(postcode);
            }
        }
        builder.setFlatNumber(flat);
        builder.setAddressString(addressString);
        if (Objects.nonNull(modType)) {
            builder.setModType(modType);
        }

        return builder.build();
    }
}
