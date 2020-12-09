package com.emulator.f9.model.market.mobility.sea.mdm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ScheduleRouteGroup {
    @Getter
    @Setter
    int regSeq;

    @Getter
    @Setter
    String locationCode;

    @Getter
    @Setter
    String locationTypeCode;
}
