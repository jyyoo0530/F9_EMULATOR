package com.emulator.f9.model.market.mobility.sea;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "F9_SEA_SKD")
public class F9_SEA_SKD {
    /// master column
    @Id
    @Getter
    @Setter
    String scheduleId;                   // --) ID serviceLane + voyageNumber + vesselKey + fromScheduleReferenceKey + toScheduleReferenceKey

    @Getter
    @Setter
    int scheduleSeq;                     // --) 시퀀스

    @Getter
    @Setter
    String dataCreationDate;             // --) 데이터 생성 일자

    @Getter
    @Setter
    String serviceLaneKey;               // --) 서비스 Grouping Code

    @Getter
    @Setter
    String serviceLaneName;               // --) 서비스 Grouping Name

    @Getter
    @Setter
    String scheduleOwnerCode;            // --) 실제로 선박 스케쥴을 운용하는 오너

    @Getter
    @Setter
    String scheduleProvider;             // --) 선박 스케쥴을 제공한 source Provider

    @Getter
    @Setter
    String voyageNumber;                 // --) 선박의 항차

    @Getter
    @Setter
    String vesselKey;                    //  1) 무엇이

    @Getter
    @Setter
    String vesselName;                    //--1) 선박명

    @Getter
    @Setter
    int vesselCapacityTeu;                //--1) 선박 크기

    @Getter
    @Setter
    String fromKey;                      //  2) 어디서 ( From, locationCode )

    @Getter
    @Setter
    int fromSeq;                          //   ) 어디서에 대한 route sequence

    @Getter
    @Setter
    String fromLocationStatus;           //   ) inland 인지 아닌지

    @Getter
    @Setter
    String fromScheduleReferenceKey;     //  3) 언제 ( From )

    @Getter
    @Setter
    String serviceProductWeek;           //   ) 상품구분코드 (POL ETD 기준)

    @Getter
    @Setter
    String fromETA;                      //   )

    @Getter
    @Setter
    String fromETB;                      //   )

    @Getter
    @Setter
    String fromETD;                      //   )

    @Getter
    @Setter
    String fromAdditionalInfoKey;        // --) from 노드에서의 추가 정보 ( 도큐멘트 등등의 기타 스케쥴 )

    @Getter
    @Setter
    String fromAlliedNodeScheduleId;     // --) 인접한 Node의 스케쥴 Id

    @Getter
    @Setter
    String toKey;                        //  4) 어디로 ( To, locationCode )

    @Getter
    @Setter
    int toSeq;                           //   ) 어디서에 대한 route sequence

    @Getter
    @Setter
    String toLocationStatus;           //   ) inland 인지 아닌지

    @Getter
    @Setter
    String toScheduleReferenceKey;       // 5) 어디로 ( To )

    @Getter
    @Setter
    String toETA;                        //  )

    @Getter
    @Setter
    String toETB;                        //  )

    @Getter
    @Setter
    String toETD;                        //  )

    @Getter
    @Setter
    String toAdditionalInfoKey;          // --) to 노드에서의 추가 정보 ( 도큐멘트 등등의 기타 스케쥴 )

    @Getter
    @Setter
    String toAlliedNodeScheduleId;       // --) 인접한 Node의 스케쥴 Id

}

