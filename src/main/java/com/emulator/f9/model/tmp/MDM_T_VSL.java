package com.emulator.f9.model.tmp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "MDM_T_VSL")
public class MDM_T_VSL {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "MDM_VSL_CD")
    @Getter
    @Setter
    String vesselCode;

    @Column(name = "MDM_VSL_NM")
    @Getter
    @Setter
    String vesselName;

    @Column(name = "MDM_CALLSIGN")
    @Getter
    @Setter
    String callSign;

    @Column(name = "MDM_IMONR")
    @Getter
    @Setter
    String imoNumber;

    @Column(name = "MDM_FLG_CD")
    @Getter
    @Setter
    String flagCode;

    @Column(name = "MDM_FLG_NM")
    @Getter
    @Setter
    String flagName;

    @Column(name = "MDM_CPCT_TEU")
    @Getter
    @Setter
    String capacityTeu;

    @Column(name = "MDM_BLD_YR")
    @Getter
    @Setter
    String buildYear;

    @Column(name = "MDM_VSL_CLS")
    @Getter
    @Setter
    String vesselClass;

    @Column(name = "MDM_DATA_OWNR")
    @Getter
    @Setter
    String dataOwner;

    @Column(name = "MDM_F9_VSL_CD")
    @Getter
    @Setter
    String f9eVslCode;
}
