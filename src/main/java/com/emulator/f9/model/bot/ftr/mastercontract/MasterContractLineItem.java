package com.emulator.f9.model.bot.ftr.mastercontract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class MasterContractLineItem {
    @Getter
    @Setter
    String baseYearWeek;

    @Getter
    @Setter
    int qty;

    @Getter
    @Setter
    List<MasterContractPrices> masterContractPrices;

    @Getter
    @Setter
    String scheduleGroupLineId;

    @Getter
    @Setter
    String deleteYn;
}
