package com.emulator.f9.model.bot.ftr.offer;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OfferAccounts {
    @Getter
    @Setter
    String accountOwnerCompanyCode;

    @Getter
    @Setter
    String bankName;

    @Getter
    @Setter
    String swiftCode;

    @Getter
    @Setter
    String accountNumber;

    @Getter
    @Setter
    String accountOwnerName;

    @Getter
    @Setter
    String routingNo;
}
