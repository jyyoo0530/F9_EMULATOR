package com.emulator.f9.model.ftr;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "SCH_GRP_RTE")
public class SCH_GRP_RTE {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "SCH_RTE_SEQ")
    @Getter
    @Setter
    int schRteSeq;

    @Column(name = "SCH_RTE_LOC_CD")
    @Getter
    @Setter
    String schRteLocCd;

    @Column(name = "SCH_RTE_LOC_TP_CD")
    @Getter
    @Setter
    String schRteLocTpCd;

    @Column(name = "SCH_GRP_ID")
    @Getter
    @Setter
    String schGrpId;
}
