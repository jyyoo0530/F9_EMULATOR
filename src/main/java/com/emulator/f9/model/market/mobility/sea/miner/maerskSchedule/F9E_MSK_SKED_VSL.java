package com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule;


import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Document(collection = "F9E_MSK_SKED_VSL")
public class F9E_MSK_SKED_VSL {
    @Id
    @Getter
    @Setter
    String id; // scheduleKey+scheduleSeq

    @Getter
    @Setter
    String scheduleKey; // SCHEDULE KEY, getSrcVslCode() + getServiceArr() + getVoyageArrival() + getSrcPortCode() + getTerminalGeoId()+MSK;

    @Getter
    @Setter
    int scheduleSeq;

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


    public void setAllData(String vesselCode, JsonObject inputData, F9E_MSK_SKED_VSL_ReactiveMongoRepository f9eMskSkedVslRepo) {
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
        setScheduleKey(getSrcVslCode() + getServiceArr() + getVoyageArrival() + getSrcPortCode() + getTerminalGeoId());     //     + getArrival().replaceAll("[^\\d.]", "")
        List<F9E_MSK_SKED_VSL> found = f9eMskSkedVslRepo.findByScheduleKey(this.getScheduleKey()).collect(Collectors.toList()).block();
        if (found.size() == 0) {
            setScheduleSeq(0);
        } else {
            F9E_MSK_SKED_VSL latestSked = Collections.max(found, Comparator.comparing(F9E_MSK_SKED_VSL::getScheduleSeq));
            if (!latestSked.getArrival().equals(this.getArrival()) || !latestSked.getDeparture().equals(this.getDeparture())) {
                int seq = latestSked.getScheduleSeq();
                setScheduleSeq(seq + 1);
            } else {
                setScheduleSeq(latestSked.getScheduleSeq());
            }
        }
        setId(this.getScheduleKey() + this.getScheduleSeq());
    }

}
