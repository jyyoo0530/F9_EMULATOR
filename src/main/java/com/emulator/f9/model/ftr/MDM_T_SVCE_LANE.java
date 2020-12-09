package com.emulator.f9.model.ftr;

import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_SVCGRP;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "MDM_T_SVCE_LANE")
public class MDM_T_SVCE_LANE {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "MDM_SVC_CD", unique =  true)
    @Getter
    @Setter
    String mdmSvcCd;

    @Column(name = "MDM_SVC_NM")
    @Getter
    @Setter
    String mdmSvcNm;

    @Column(name = "MDM_OWNR_CD")
    @Getter
    @Setter
    String mdmOwnrCd;

    @Column(name = "MDM_OWNR_SVC_CD")
    @Getter
    @Setter
    String mdmOwnrSvcCd;

    public void setAllData(F9_MDM_SVCGRP f9MdmSvcgrp){
        setMdmOwnrCd(f9MdmSvcgrp.getMdmOwnerCode());
        setMdmSvcCd(f9MdmSvcgrp.getServiceCode());
        setMdmSvcNm(f9MdmSvcgrp.getServiceName());
        setMdmOwnrSvcCd(f9MdmSvcgrp.getMdmOwnerSvcCd());
    }
}
