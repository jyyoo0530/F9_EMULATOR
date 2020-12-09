package com.emulator.f9.model.ftr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "SCH_GRP")
public class SCH_GRP {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "SCH_GRP_ID", unique = true)
    @Getter
    @Setter
    String schGrpId;

    @Column(name = "SCH_GRP_SVC_CD")
    @Getter
    @Setter
    String schGrpSvcCd;

    @Column(name = "SCH_GRP_SVC_NM")
    @Getter
    @Setter
    String schGrpSvcNm;

    @Column(name = "SCH_GRP_SVC_DIR")
    @Getter
    @Setter
    String schGrpSvcDir;

    @Column(name = "SCH_GRP_VOL")
    @Getter
    @Setter
    int schGrpVol;

}
