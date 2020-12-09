package com.emulator.f9.model.ftr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "FMC_MSTR_CTRK_CRYR")
public class FMC_MSTR_CTRK_CRYR {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "CRE_USR_ID")
    @Getter
    @Setter
    String creUsrId;

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

    @Column(name = "MSTR_CTRK_ID")
    @Getter
    @Setter
    int mstrCtrkId;
}
