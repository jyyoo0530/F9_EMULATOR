package com.emulator.f9.model.bot.ftr.mastercontract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
public class MasterContractRoutes {
    @Getter
    @Setter
    int regSeq;

    @Getter
    @Setter
    String locationCode;

    @Getter
    @Setter
    String locationTypeCode;

    @Getter
    @Setter
    String deleteYn;
}
