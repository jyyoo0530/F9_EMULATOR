package com.emulator.f9.model.market.mobility.sea.mdm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "F9_SEA_SKD_GRP")
public class ScheduleLineGroup {
    @Id
    @Getter
    @Setter
    String scheduleGroupId;

    @Getter
    @Setter
    String productWeek;

    @Getter
    @Setter
    String vesselCode;

    @Getter
    @Setter
    int vesselCapacityTeu;

    @Getter
    @Setter
    List<ScheduleAbstractDetail> scheduleAbstractDetails;

}
