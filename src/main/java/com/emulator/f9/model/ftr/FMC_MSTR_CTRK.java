package com.emulator.f9.model.ftr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "FMC_MSTR_CTRK")
public class FMC_MSTR_CTRK {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "CRE_USR_ID")
    @Getter
    @Setter
    String creUsrId;

    @Column(name = "CRE_DT")
    @Getter
    @Setter
    String creDT;

    @Column(name = "UPD_USR_ID")
    @Getter
    @Setter
    String updUsrId;

    @Column(name = "UPD_DT")
    @Getter
    @Setter
    String updDt;

    @Column(name = "CRYR_CD")
    @Getter
    @Setter
    String cryrCd;

    @Column(name = "DEL_YN")
    @Getter
    @Setter
    String delYn;

    @Column(name = "MSTR_CTRK_NR")
    @Getter
    @Setter
    String mstrCtrkNr;

    @Column(name = "PYMT_PLAN_CD")
    @Getter
    @Setter
    String pymtPlanCd;

    @Column(name = "PYMT_TRM_CD")
    @Getter
    @Setter
    String pymtTrmCd;

    @Column(name = "RD_TRM_CD")
    @Getter
    @Setter
    String rdTrmCd;

    @Column(name = "SVCE_LANE_CD")
    @Getter
    @Setter
    String svceLaneCd;

}
