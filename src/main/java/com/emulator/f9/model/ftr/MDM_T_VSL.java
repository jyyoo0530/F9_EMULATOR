package com.emulator.f9.model.ftr;

import com.emulator.f9.model.market.mobility.sea.mdm.F9_MDM_VSL;
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


    @Column(name = "MDM_VSL_CD", unique = true)
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

    public void setAllData(F9_MDM_VSL f9MdmVsl) {
        setBuildYear(f9MdmVsl.getBuildYear());
        setCallSign(f9MdmVsl.getCallSign());
        setCapacityTeu(f9MdmVsl.getCapacityTeu());
        setDataOwner(f9MdmVsl.getDataOwner());
        setF9eVslCode(f9MdmVsl.getF9eVslCode());
        setFlagCode(f9MdmVsl.getFlagCode());
        setFlagName(f9MdmVsl.getFlagName());
        setImoNumber(f9MdmVsl.getImoNumber());
        setVesselClass(f9MdmVsl.getVesselClass());
        setVesselCode(f9MdmVsl.getVesselCode());
        setVesselName(f9MdmVsl.getVesselName());
    }
}
