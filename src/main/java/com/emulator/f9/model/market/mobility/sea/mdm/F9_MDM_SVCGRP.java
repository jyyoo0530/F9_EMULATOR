package com.emulator.f9.model.market.mobility.sea.mdm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

@Data
@Document(collection = "F9_MDM_SVCGRP")
public class F9_MDM_SVCGRP {
    @Id
    @Getter
    @Setter
    String serviceCode; // mdmOwnerCode+mdmOwnerServicegrpId

    @Getter
    @Setter
    String serviceName;

    @Getter
    @Setter
    String mdmOwnerCode;

    @Getter
    @Setter
    String mdmOwnerSvcCd;
}
