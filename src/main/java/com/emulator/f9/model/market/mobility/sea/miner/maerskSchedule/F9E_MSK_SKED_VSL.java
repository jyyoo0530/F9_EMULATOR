package com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.web.client.RestTemplate;

@Data
public class F9E_MSK_SKED_VSL {
    @Id
    @Getter
    @Setter
    String scheduleKey; // SCHEDULE ID, portGeoId+terminalGeoId+voyageArrival;

    @Getter
    @Setter
    String srcVslCode; // src VESSEL KEY

    @Getter
    @Setter
    String srcPortCode; // src PORT KEY

    @Getter
    @Setter
    String arrival;

    @Getter
    @Setter
    String departure;

    @Getter
    @Setter
    String serviceArr;

    @Getter
    @Setter
    String serviceDep;

    @Getter
    @Setter
    String terminal;

    @Getter
    @Setter
    String terminalGeoId;

    @Getter
    @Setter
    String voyageArrival;

    @Getter
    @Setter
    String voyageDeparture;

    public void setAllData(String vesselCode, JsonObject inputData) {
        setSrcVslCode(vesselCode);
        setSrcPortCode(inputData.get("portGeoId").toString().replace("\"", ""));
        setArrival(inputData.get("arrival").toString().replace("\"", ""));
        setDeparture(inputData.get("departure").toString().replace("\"", ""));
        setServiceArr(inputData.get("serviceArr").toString().replace("\"", ""));
        setServiceDep(inputData.get("serviceDep").toString().replace("\"", ""));
        setTerminal(inputData.get("terminal").toString().replace("\"", ""));
        setTerminalGeoId(inputData.get("terminalGeoId").toString().replace("\"", ""));
        setVoyageArrival(inputData.get("voyageArrival").toString().replace("\"", ""));
        setVoyageDeparture(inputData.get("voyageDeparture").toString().replace("\"", ""));
        setScheduleKey(getSrcVslCode() + getSrcPortCode() + getArrival().replaceAll("[^\\d.]", ""));
    }

}
