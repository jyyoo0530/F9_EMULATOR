package com.emulator.f9.model.bot.ftr.mastercontract;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.util.List;

@Data
public class MasterContract {
    @Getter
    @Setter
    String paymentPlanCode;

    @Getter
    @Setter
    String carrierCode;

    @Getter
    @Setter
    String deleteYn;

    @Getter
    @Setter
    String rdTermCode;

    @Getter
    @Setter
    String paymentTermCode;

    @Getter
    @Setter
    String serviceLaneCode;

    @Getter
    @Setter
    List<MasterContractLineItem> masterContractLineItems;

    @Getter
    @Setter
    List<MasterContractCarriers> masterContractCarriers;

    @Getter
    @Setter
    List<MasterContractCargoType> masterContractCargoTypes;

    @Getter
    @Setter
    List<MasterContractRoutes> masterContractRoutes;
}
