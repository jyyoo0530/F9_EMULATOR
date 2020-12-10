package com.emulator.f9.model.ftr.membership;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "FMM_ACC")
public class FMM_ACC {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "ACC_NR")
    @Getter
    @Setter
    String accNr;

    @Column(name = "ACC_OWN_CO_CD")
    @Getter
    @Setter
    String accOwnCoCd;

    @Column(name = "ACC_OWN_NM")
    @Getter
    @Setter
    String accOwnNm;

    @Column(name = "BNK_NM")
    @Getter
    @Setter
    String bnkNm;

    @Column(name = "RTE_NO")
    @Getter
    @Setter
    String rteNo;

    @Column(name = "SWIFT_NO")
    @Getter
    @Setter
    String swiftNo;
}
