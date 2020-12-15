package com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule;

import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_LOCATION_ReactiveMongoRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Data
public class F9E_MSK_PORTMDM {
    @Getter
    @Setter
    ArrayList<String> brandNames;

    @Getter
    @Setter
    ArrayList<String> brands;

    @Getter
    @Setter
    String cityName;

    @Getter
    @Setter
    String countryCode;

    @Getter
    @Setter
    String countryGeoId;

    @Getter
    @Setter
    String countryName;

    @Getter
    @Setter
    String maerskGeoLocationId;

    @Getter
    @Setter
    String maerskRkstCode;

    @Getter
    @Setter
    String maerskRktsCode;

    @Getter
    @Setter
    String timezoneId;

    @Getter
    @Setter
    String type;

    @Getter
    @Setter
    String unLocCode;

    // --) 병렬 처리로 변경 필요.
    public void setAllData(String stringResponse) {
        JsonObject inputData = new JsonObject();
        try {
            inputData = new JsonParser().parse(stringResponse).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> brndNms = new ArrayList<>();
        ArrayList<String> brnds = new ArrayList<>();

        try {
            setCityName(inputData.get("cityName").toString().replace("\"", ""));
        } catch (Exception e) {
            setCityName("UNDEFINED");
        }
        try {
            setCountryCode(inputData.get("countryCode").toString().replace("\"", ""));
        } catch (Exception e) {
            setCountryCode("UNDEFINED");
        }
        try {
            setCountryGeoId(inputData.get("countryGeoId").toString().replace("\"", ""));
        } catch (Exception e) {
            setCountryGeoId("UNDEFINED");
        }
        try {
            setCountryName(inputData.get("countryName").toString().replace("\"", ""));
        } catch (Exception e) {
            setCountryName("UNDEFINED");
        }
        try {
            setMaerskGeoLocationId(inputData.get("maerskGeoLocationId").toString().replace("\"", ""));
        } catch (Exception e) {
            setMaerskGeoLocationId("UNDEFINED");
        }
        try {
            setMaerskRkstCode(inputData.get("maerskRkstCode").toString().replace("\"", ""));
        } catch (Exception e) {
            setMaerskRkstCode("UNDEFINED");
        }
        try {
            setMaerskRktsCode(inputData.get("maerskRktsCode").toString().replace("\"", ""));
        } catch (Exception e) {
            setMaerskRktsCode("UNDEFINED");
        }
        try {
            setTimezoneId(inputData.get("timezoneId").toString().replace("\"", ""));
        } catch (Exception e) {
            setTimezoneId("UNDEFINED");
        }
        try {
            setType(inputData.get("type").toString().replace("\"", ""));
        } catch (Exception e) {
            setType("UNDEFINED");
        }
        try {
            setUnLocCode(inputData.get("unLocCode").toString().replace("\"", ""));
        } catch (Exception e) {
            setUnLocCode("UNDEFINED");
        }
        try {
            inputData.get("brandNames").getAsJsonArray().forEach(x -> brndNms.add(x.toString().replace("\"", "")));
            setBrandNames(brndNms);
        } catch (Exception e) {
            brndNms.add("UNDEFINED");
            setBrandNames(brndNms);
        }
        try {
            inputData.get("brands").getAsJsonArray().forEach(x -> brnds.add(x.toString().replace("\"", "")));
            setBrands(brnds);
        } catch (Exception e) {
            brnds.add("UNDEFINED");
            setBrands(brnds);
        }
    }

}
