package com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class F9E_MSK_VSLMDM {
    @Getter
    @Setter
    String buildYear;
    @Getter
    @Setter
    String callSign;
    @Getter
    @Setter
    String capacityTeu;
    @Getter
    @Setter
    String vesselClass;
    @Getter
    @Setter
    String flagCode;
    @Getter
    @Setter
    String flagName;
    @Getter
    @Setter
    String imoNumber;
    @Getter
    @Setter
    String longName;
    @Getter
    @Setter
    String maerskId;
    @Getter
    @Setter
    String name;

    public void setAllData(String stringResponse) {
        JsonObject inputData = new JsonParser().parse(stringResponse).getAsJsonObject();
        try {
            setBuildYear(inputData.get("buildYear").toString().replace("\"", ""));
        } catch (Exception e) {
            setBuildYear("UNDEFINED");
        }
        try {
            setCallSign(inputData.get("callSign").toString().replace("\"", ""));
        } catch (Exception e) {
            setCallSign("UNDEFINED");
        }
        try {
            setCapacityTeu(inputData.get("capacityTeu").toString().replace("\"", ""));
        } catch (Exception e) {
            setCapacityTeu("UNDEFINED");
        }
        try {
            setVesselClass(inputData.get("vesselClass").toString().replace("\"", ""));
        } catch (Exception e) {
            setVesselClass("UNDEFINED");
        }
        try {
            setFlagCode(inputData.get("flagCode").toString().replace("\"", ""));
        } catch (Exception e) {
            setFlagCode("UNDEFINED");
        }
        try {
            setFlagName(inputData.get("flagName").toString().replace("\"", ""));
        } catch (Exception e) {
            setFlagName("UNDEFINED");
        }
        try {
            setImoNumber(inputData.get("imoNumber").toString().replace("\"", ""));
        } catch (Exception e) {
            setImoNumber("UNDEFINED");
        }
        try {
            setLongName(inputData.get("longName").toString().replace("\"", ""));
        } catch (Exception e) {
            setLongName(inputData.get("name").toString().replace("\"\"", ""));
        }
        try {
            setMaerskId(inputData.get("maerskId").toString().replace("\"", ""));
        } catch (Exception e) {
            setMaerskId("UNDEFINED");
        }
        try {
            setName(inputData.get("name").toString().replace("\"", ""));
        } catch (Exception e) {
            setName(inputData.get("name").toString().replace("\"\"", ""));
        }

    }
    public void setDummyData(String dummyCode, String dummyName) {
        try {
            setBuildYear("DummyBuildYear");
        } catch (Exception e) {
            setBuildYear("UNDEFINED");
        }
        try {
            setCallSign("DummyCallSign");
        } catch (Exception e) {
            setCallSign("UNDEFINED");
        }
        try {
            setCapacityTeu("DummyCapcityTeu");
        } catch (Exception e) {
            setCapacityTeu("UNDEFINED");
        }
        try {
            setVesselClass("DummyVesselClass");
        } catch (Exception e) {
            setVesselClass("UNDEFINED");
        }
        try {
            setFlagCode("DummyFlagCode");
        } catch (Exception e) {
            setFlagCode("UNDEFINED");
        }
        try {
            setFlagName("DummyFlagName");
        } catch (Exception e) {
            setFlagName("UNDEFINED");
        }
        try {
            setImoNumber("DummyImoNumber");
        } catch (Exception e) {
            setImoNumber("UNDEFINED");
        }
        try {
            setLongName(dummyName);
        } catch (Exception e) {
            setImoNumber("UNDEFINED");
        }
        try {
            setMaerskId(dummyCode);
        } catch (Exception e) {
            setMaerskId("UNDEFINED");
        }
        try {
            setName(dummyName);
        } catch (Exception e) {
            setName("UNDEFINED");
        }

    }

}
