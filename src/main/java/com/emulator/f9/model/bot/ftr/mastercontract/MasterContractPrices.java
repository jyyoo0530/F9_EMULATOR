package com.emulator.f9.model.bot.ftr.mastercontract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
public class MasterContractPrices {
    @Getter
    @Setter
    String containerTypeCode;

    @Getter
    @Setter
    String containerSizeCode;

    @Getter
    @Setter
    int price;

    @Getter
    @Setter
    String deleteYn;
}
