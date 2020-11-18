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
    String dataCreationDate;

    @Getter
    @Setter
    String serviceId;

    @Getter
    @Setter
    int serviceSeq;

    @Getter
    @Setter
    String serviceLaneCode;

    @Getter
    @Setter
    String serviceLaneName;

    @Getter
    @Setter
    String serviceProductWeek; // POL ETD 기준

    @Getter
    @Setter
    String dataOwner;

    @Getter
    @Setter
    String referenceDataOwnerCode;

    /// lineItem Column
    @Getter
    @Setter
    String vesselId;

    @Getter
    @Setter
    String vesselVoyage;

    @Getter
    @Setter
    String serviceDirection;

    @Getter
    @Setter
    int polSeq;

    @Getter
    @Setter
    String polLocationCode;

    @Getter
    @Setter
    String polETA;

    @Getter
    @Setter
    String polETB;

    @Getter
    @Setter
    String polETD;

    @Getter
    @Setter
    String polLocalTimelineId; // POL에서 발생 기타 TIMELINE에 대한 별도 테이블의 키 코드.할 (Documentation관련등등...)

    @Getter
    @Setter
    String preNodeScheduleId; /// POL 이전의 노드에 대한 Connection

    @Getter
    @Setter
    int podSeq;

    @Getter
    @Setter
    String podLocationCode;

    @Getter
    @Setter
    String podETA;

    @Getter
    @Setter
    String podETB;

    @Getter
    @Setter
    String podETD;

    @Getter
    @Setter
    String podLocalTimelineId; // POD에서 발생 기타 TIMELINE에 대한 별도 테이블의 키 코드.할 (Documentation관련등등...)

    @Getter
    @Setter
    String postNodeScheduleId; /// POD 이의 노드에 대한 Connection

}
