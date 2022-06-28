package ru.fau.nia.dto.ral;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class RalAddress {
    private RalAddressOksm country;

    private String name;

    private RalAddressFiasAddrobj regionRf;

    private RalAddressFiasAddrobj districtRf;

    private RalAddressFiasAddrobj city;

    private RalAddressFiasAddrobj locality;

    private RalAddressFiasAddrobj street;

    private RalAddressHouse house;

    private String flat;

    private String postcode;

    private Collection<RalBusinessLineType> businessLineTypes = new ArrayList<>();
}
