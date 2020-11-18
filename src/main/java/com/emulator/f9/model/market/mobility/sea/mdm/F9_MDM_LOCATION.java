package com.emulator.f9.model.market.mobility.sea.mdm;

import com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule.F9E_MSK_PORTMDM;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "F9_MDM_LOCATION")
@JsonPropertyOrder({"regionCode",
        "regionName",
        "subRegionCode",
        "subRegionName",
        "countryCode",
        "countryName",
        "subCountryCode",
        "subCountryName",
        "locationCode",
        "locationName",
        "locationNameWithDiacritics",
        "locationStatus",
        "locationFunction",
        "locationDate",
        "locationLatitude",
        "locationLongitude",
        "mdmOwnerCode",
        "mdmOwnerLocationId",
        "f9LocationId"})
public class F9_MDM_LOCATION {
    @Getter
    @Setter
    String regionCode;

    @Getter
    @Setter
    String regionName;

    @Getter
    @Setter
    String subRegionCode;

    @Getter
    @Setter
    String subRegionName;

    @Getter
    @Setter
    String countryCode;

    @Getter
    @Setter
    String countryName;

    @Getter
    @Setter
    String subCountryCode;

    @Getter
    @Setter
    String subCountryName;

    @Getter
    @Setter
    String locationCode;

    @Getter
    @Setter
    String locationName;

    @Getter
    @Setter
    String locationNameWithDiacritics;

    @Getter
    @Setter
    String locationStatus;

    @Getter
    @Setter
    String locationFunction;

    @Getter
    @Setter
    String locationDate;

    @Getter
    @Setter
    String locationLatitude;

    @Getter
    @Setter
    String locationLongitude;

    @Getter
    @Setter
    String mdmOwnerCode;


    @Getter
    @Setter
    String mdmOwnerLocationId;

    @Id
    @Getter
    @Setter
    String f9LocationId; // dataowner + mdmownerlocationid


}
