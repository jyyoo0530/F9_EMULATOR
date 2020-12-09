package com.emulator.f9.model.ftr;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "SCH_GRP_DETAILS")
public class SCH_GRP_DETAILS {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "SCH_ID")
    @Getter
    @Setter
    String schId;

    @Column(name = "SCH_SEQ")
    @Getter
    @Setter
    int schSeq;

    @Column(name = "SCH_GRP_LINE_ID")
    @Getter
    @Setter
    String schSeqLineId;
}
