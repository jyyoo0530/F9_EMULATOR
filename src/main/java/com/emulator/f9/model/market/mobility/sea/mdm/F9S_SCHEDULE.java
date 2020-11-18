package com.emulator.f9.model.market.mobility.sea.mdm;

import com.google.gson.JsonArray;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Data
public class F9S_SCHEDULE {
    @Setter
    @Getter
    private String serviceLaneCode;
    @Setter
    @Getter
    private String vesselName;
    @Setter
    @Getter
    private String serviceLaneName;
    @Setter
    @Getter
    private String vesselFlag;
    @Setter
    @Getter
    private String vesselCapacity;
    @Setter
    @Getter
    private String voyageCode;
    @Setter
    @Getter
    private String directionCode;
    @Setter
    @Getter
    private int polSeq;
    @Setter
    @Getter
    private String polCode;
    @Setter
    @Getter
    private String polName;
    @Setter
    @Getter
    private String polCountryCode;
    @Setter
    @Getter
    private String polCountryName;
    @Setter
    @Getter
    private String polSubRegionCode;
    @Setter
    @Getter
    private String polSubRegionName;
    @Setter
    @Getter
    private String polRegionCode;
    @Setter
    @Getter
    private String polRegionName;
    @Setter
    @Getter
    private String polETADate;
    @Setter
    @Getter
    private String polETATime;
    @Setter
    @Getter
    private String polETDDate;
    @Setter
    @Getter
    private String polETDTime;
    @Setter
    @Getter
    private String productYearWeek;
    @Setter
    @Getter
    private String polServiceYearWeek;
    @Setter
    @Getter
    private int podSeq;
    @Setter
    @Getter
    private String podCode;
    @Setter
    @Getter
    private String podName;
    @Setter
    @Getter
    private String podCountryCode;
    @Setter
    @Getter
    private String podCountryName;
    @Setter
    @Getter
    private String podSubRegionCode;
    @Setter
    @Getter
    private String podSubRegionName;
    @Setter
    @Getter
    private String podRegionCode;
    @Setter
    @Getter
    private String podRegionName;
    @Setter
    @Getter
    private String podETADate;
    @Setter
    @Getter
    private String podETATime;
    @Setter
    @Getter
    private String podETDDate;
    @Setter
    @Getter
    private String podETDTime;
    @Setter
    @Getter
    private String podServiceYearWeek;
    @Setter
    @Getter
    private String ownerCode;
    @Setter
    @Getter
    private String scheduleStatus;
    @Setter
    @Getter
    private String deleteYn;

    public JsonArray generateSchedule(String status, JsonArray baseSchedule) {
        JsonArray result = new JsonArray();

        return result;
    }


}
