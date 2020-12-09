package com.emulator.f9.model.bot.ftr.offer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OfferPrices {
    @Getter
    @Setter
    String containerSizeCode;

    @Getter
    @Setter
    String containerTypeCode;

    @Getter
    @Setter
    double offerPrice;
}
