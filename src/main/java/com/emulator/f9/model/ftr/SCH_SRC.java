package com.emulator.f9.model.ftr;

import com.emulator.f9.model.market.mobility.sea.F9_SEA_SKD;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "SCH_SRC")
public class SCH_SRC {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    /// master column
    @Column(name = "SCH_ID", unique = true)
    @Getter
    @Setter
    String scheduleId;                   // --) ID

    @Column(name = "SCH_SEQ")
    @Getter
    @Setter
    int scheduleSeq;                     // --) 시퀀스

    @Column(name = "SCH_CRE_DTE")
    @Getter
    @Setter
    String dataCreationDate;             // --) 데이터 생성 일자

    @Column(name = "SCH_SVC_KEY")
    @Getter
    @Setter
    String serviceLaneKey;               // --) 서비스 Grouping Code

    @Column(name = "SCH_SVC_NM")
    @Getter
    @Setter
    String serviceLaneName;               // --) 서비스 Grouping Name

    @Column(name = "SCH_OWNR_CD")
    @Getter
    @Setter
    String scheduleOwnerCode;            // --) 실제로 선박 스케쥴을 운용하는 오너

    @Column(name = "SCH_PROV")
    @Getter
    @Setter
    String scheduleProvider;             // --) 선박 스케쥴을 제공한 source Provider

    @Column(name = "SCH_VOY_NR")
    @Getter
    @Setter
    String voyageNumber;                 // --) 선박의 항차

    @Column(name = "SCH_VSL_KEY")
    @Getter
    @Setter
    String vesselKey;                    //  1) 무엇이

    @Column(name = "SCH_VSL_NM")
    @Getter
    @Setter
    String vesselName;                    //--1) 선박명

    @Column(name = "SCH_VSL_SIZE")
    @Getter
    @Setter
    int vesselCapacityTeu;                 //--1) 선박크기

    @Column(name = "SCH_POL_KEY")
    @Getter
    @Setter
    String fromKey;                      //  2) 어디서 ( From, locationCode )

    @Column(name = "SCH_POL_SEQ")
    @Getter
    @Setter
    int fromSeq;                          //   ) 어디서에 대한 route sequence

    @Column(name = "SCH_POL_TP")
    @Getter
    @Setter
    String fromLocationStatus;           //   ) inland 인지 아닌지

    @Column(name = "SCH_POL_SKD_KEY")
    @Getter
    @Setter
    String fromScheduleReferenceKey;     //  3) 언제 ( From )

    @Column(name = "SCH_POL_WK")
    @Getter
    @Setter
    String serviceProductWeek;           //   ) 상품구분코드 (POL ETD 기준)

    @Column(name = "SCH_POL_ETA")
    @Getter
    @Setter
    String fromETA;                      //   )

    @Column(name = "SCH_POL_ETB")
    @Getter
    @Setter
    String fromETB;                      //   )

    @Column(name = "SCH_POL_ETD")
    @Getter
    @Setter
    String fromETD;                      //   )

    @Column(name = "SCH_POL_ADINFO_KEY")
    @Getter
    @Setter
    String fromAdditionalInfoKey;        // --) from 노드에서의 추가 정보 ( 도큐멘트 등등의 기타 스케쥴 )

    @Column(name = "SCH_POL_PRE_ND_KEY")
    @Getter
    @Setter
    String fromAlliedNodeScheduleId;     // --) 인접한 Node의 스케쥴 Id

    @Column(name = "SCH_POD_KEY")
    @Getter
    @Setter
    String toKey;                        //  4) 어디로 ( To, locationCode )

    @Column(name = "SCH_POD_SEQ")
    @Getter
    @Setter
    int toSeq;                           //   ) 어디서에 대한 route sequence

    @Column(name = "SCH_POD_TP")
    @Getter
    @Setter
    String toLocationStatus;           //   ) inland 인지 아닌지

    @Column(name = "SCH_POD_SKD_KEY")
    @Getter
    @Setter
    String toScheduleReferenceKey;       // 5) 어디로 ( To )

    @Column(name = "SCH_POD_ETA")
    @Getter
    @Setter
    String toETA;                        //  )

    @Column(name = "SCH_POD_ETB")
    @Getter
    @Setter
    String toETB;                        //  )

    @Column(name = "SCH_POD_ETD")
    @Getter
    @Setter
    String toETD;                        //  )

    @Column(name = "SCH_POD_ADINFO_KEY")
    @Getter
    @Setter
    String toAdditionalInfoKey;          // --) to 노드에서의 추가 정보 ( 도큐멘트 등등의 기타 스케쥴 )

    @Column(name = "SCH_POD_PST_ND_KEY")
    @Getter
    @Setter
    String toAlliedNodeScheduleId;       // --) 인접한 Node의 스케쥴 Id

    public void setAllData(F9_SEA_SKD f9SeaSkd) {
        setScheduleId(f9SeaSkd.getScheduleId());
        setDataCreationDate(f9SeaSkd.getDataCreationDate());
        setFromAdditionalInfoKey(f9SeaSkd.getFromAdditionalInfoKey());
        setFromAlliedNodeScheduleId(f9SeaSkd.getFromAlliedNodeScheduleId());
        setFromETA(f9SeaSkd.getFromETA());
        setFromETB(f9SeaSkd.getFromETB());
        setFromETD(f9SeaSkd.getFromETD());
        setFromKey(f9SeaSkd.getFromKey());
        setFromLocationStatus(f9SeaSkd.getFromLocationStatus());
        setFromScheduleReferenceKey(f9SeaSkd.getFromScheduleReferenceKey());
        setFromSeq(f9SeaSkd.getFromSeq());
        setScheduleOwnerCode(f9SeaSkd.getScheduleOwnerCode());
        setScheduleProvider(f9SeaSkd.getScheduleProvider());
        setScheduleSeq(f9SeaSkd.getScheduleSeq());
        setServiceLaneKey(f9SeaSkd.getServiceLaneKey());
        setServiceLaneName(f9SeaSkd.getServiceLaneName());
        setServiceProductWeek(f9SeaSkd.getServiceProductWeek());
        setToAdditionalInfoKey(f9SeaSkd.getToAdditionalInfoKey());
        setToAlliedNodeScheduleId(f9SeaSkd.getToAlliedNodeScheduleId());
        setToETA(f9SeaSkd.getToETA());
        setToETB(f9SeaSkd.getToETB());
        setToETD(f9SeaSkd.getToETD());
        setToKey(f9SeaSkd.getToKey());
        setToLocationStatus(f9SeaSkd.getToLocationStatus());
        setToScheduleReferenceKey(f9SeaSkd.getToScheduleReferenceKey());
        setToSeq(f9SeaSkd.getToSeq());
        setVesselKey(f9SeaSkd.getVesselKey());
        setVesselName(f9SeaSkd.getVesselName());
        setVesselCapacityTeu(f9SeaSkd.getVesselCapacityTeu());
        setVoyageNumber(f9SeaSkd.getVoyageNumber());
    }

}
