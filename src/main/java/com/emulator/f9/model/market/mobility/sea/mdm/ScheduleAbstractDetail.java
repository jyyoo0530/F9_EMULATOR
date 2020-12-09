package com.emulator.f9.model.market.mobility.sea.mdm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ScheduleAbstractDetail {
    @Getter
    @Setter
    String scheduleId;

    @Getter
    @Setter
    int initialScheduleSeq;
}
