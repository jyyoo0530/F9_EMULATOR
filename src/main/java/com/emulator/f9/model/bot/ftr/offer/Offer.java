package com.emulator.f9.model.bot.ftr.offer;

import com.emulator.f9.model.bot.ftr.mastercontract.MasterContractCargoType;
import com.emulator.f9.model.bot.ftr.mastercontract.MasterContractCarriers;
import com.emulator.f9.model.bot.ftr.mastercontract.MasterContractLineItem;
import com.emulator.f9.model.bot.ftr.mastercontract.MasterContractRoutes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Offer {
    @Getter
    @Setter
    String allYn;

    @Getter
    @Setter
    String expireYn;

    @Getter
    @Setter
    String masterContractNumber;

    @Getter
    @Setter
    String offerPaymentPlanCode;

    @Getter
    @Setter
    String offerPaymentTermCode;

    @Getter
    @Setter
    String offerRdTermCode;

    @Getter
    @Setter
    String offerTransactionTypeCode;

    @Getter
    @Setter
    String offerTypeCode;


    @Getter
    @Setter
    String timeInForceCode;


    @Getter
    @Setter
    String tradeCompanyCode;


    @Getter
    @Setter
    String tradeMarketTypeCode;


    @Getter
    @Setter
    String tradeRoleCode;


    @Getter
    @Setter
    String virtualGroupAgreementYn;


    @Getter
    @Setter
    String virtualGroupOfferYn;


    @Getter
    @Setter
    String whatIfAgreementYn;


    @Getter
    @Setter
    List<OfferLineItems> offerLineItems;

    @Getter
    @Setter
    List<OfferCarriers> offerCarriers;

    @Getter
    @Setter
    List<OfferRoutes> offerRoutes;
}
