package com.emulator.f9.model.ftr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity (name = "FMC_MSTR_CTRK_PRCE_LST")
public class FMC_MSTR_CTRK_PRCE_LST {
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

    @Column(name = "BSE_YW")
    @Getter
    @Setter
    String bseYw;

    @Column(name = "TRDE_CONT_SIZE_CD")
    @Getter
    @Setter
    String trdeContSizeCd;

    @Column(name = "TRDE_CONT_TP_CD")
    @Getter
    @Setter
    String trdeContTpCd;

    @Column(name = "DEL_YN")
    @Getter
    @Setter
    String delYn;

    @Column(name = "MSTR_CTRK_NR")
    @Getter
    @Setter
    String mstrCtrkNr;

    @Column(name = "PRCE")
    @Getter
    @Setter
    double price;

    @Column(name = "ITEM_ID")
    @Getter
    @Setter
    int itemId;


}
