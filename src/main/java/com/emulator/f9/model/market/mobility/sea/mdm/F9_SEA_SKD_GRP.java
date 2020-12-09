package com.emulator.f9.model.market.mobility.sea.mdm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "F9_SEA_SKD_GRP")
public class F9_SEA_SKD_GRP {

    @Getter
    @Setter
    String serviceCode;

    @Getter
    @Setter
    String serviceName;

    @Getter
    @Setter
    String serviceDirection;

    @Getter
    @Setter
    int weeklySupplyTeu;

    @Getter
    @Setter
    List<ScheduleLineGroup> scheduleLineGroup;

    @Getter
    @Setter
    List<ScheduleRouteGroup> scheduleRouteGroup;
}
