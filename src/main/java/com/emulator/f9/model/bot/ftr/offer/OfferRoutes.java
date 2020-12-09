package com.emulator.f9.model.bot.ftr.offer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OfferRoutes {
    @Getter
    @Setter
    String locationCode;

    @Getter
    @Setter
    String locationName;

    @Getter
    @Setter
    String locationTypeCode;

    @Getter
    @Setter
    int offerRegSeq;
}
