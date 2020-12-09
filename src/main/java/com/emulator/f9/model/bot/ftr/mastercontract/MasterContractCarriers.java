package com.emulator.f9.model.bot.ftr.mastercontract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MasterContractCarriers {
    @Getter
    @Setter
    String carrierCode;

    @Getter
    @Setter
    String deleteYn;
}
