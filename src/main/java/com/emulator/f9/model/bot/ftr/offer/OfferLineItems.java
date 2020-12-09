package com.emulator.f9.model.bot.ftr.offer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;

@Data
public class OfferLineItems {
    @Getter
    @Setter
    double balancedPaymentRatio;

    @Getter
    @Setter
    String baseYearWeek;

    @Getter
    @Setter
    double firstPaymentRatio;

    @Getter
    @Setter
    double middlePaymentRatio;

    @Getter
    @Setter
    double offerPrice;

    @Getter
    @Setter
    int offerQty;

    @Getter
    @Setter
    String tradeContainerSizeCode;

    @Getter
    @Setter
    String tradeContainerTypeCode;

    @Getter
    @Setter
    List<OfferPrices> offerPrices;
}
