package com.emulator.f9.model.market.mobility.sea.mdm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class F9_MDM_DATAOWNER {

    @Getter
    @Setter
    String ownerCode;

    @Getter
    @Setter
    String ownerName;

    @Getter
    @Setter
    boolean status;

}
