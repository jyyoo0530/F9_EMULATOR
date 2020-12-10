package com.emulator.f9.model.ftr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "SCH_GRP_RGN")
public class SCH_GRP_RGN {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "MDM_RGN_CD")
    @Getter
    @Setter
    String mdmRgnCd;

    @Column(name = "MDM_RGN_NM")
    @Getter
    @Setter
    String mdmRgnNm;

    @Column(name = "SCH_GRP_RGN_TP_CD")
    @Getter
    @Setter
    String schGrpRgnTpCd; // '02' == from, '03' == to

    @Column(name = "SCH_GRP_ID")
    @Getter
    @Setter
    String schGrpId;
}
