package com.emulator.f9.model.ftr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "SCH_GRP_LINE_ITEM")
public class SCH_GRP_LINE_ITEM {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "SCH_GRP_LINE_ID")
    @Getter
    @Setter
    String schGrpLnId;

    @Column(name = "SCH_GRP_LINE_PRD_WK")
    @Getter
    @Setter
    String schGrpLnPrdWk;

    @Column(name = "SCH_GRP_LINE_VSL_CD")
    @Getter
    @Setter
    String schGrpLnVslCd;

    @Column(name = "SCH_GRP_LINE_VSL_CAP")
    @Getter
    @Setter
    int schGrpLnVslCap;

    @Column(name = "SCH_GRP_ID")
    @Getter
    @Setter
    String schGrpId;

}
