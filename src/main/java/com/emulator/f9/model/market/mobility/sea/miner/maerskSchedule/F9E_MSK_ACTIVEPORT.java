package com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class F9E_MSK_ACTIVEPORT {
    @Getter
    @Setter
    String countryName;

    @Getter
    @Setter
    String locationName;

    @Getter
    @Setter
    String geoId;

    public void setAllData(JsonObject response) {
        setCountryName(response.get("countryName").toString().replace("\"", ""));
        setLocationName(response.get("locationName").toString().replace("\"", ""));
        setGeoId(response.get("geoId").toString().replace("\"", ""));
    }
}
