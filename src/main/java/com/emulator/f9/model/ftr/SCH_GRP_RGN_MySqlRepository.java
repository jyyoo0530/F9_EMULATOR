package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;

public interface SCH_GRP_RGN_MySqlRepository extends CrudRepository<SCH_GRP_RGN, String> {
    SCH_GRP_RGN findBySchGrpIdAndSchGrpRgnTpCdAndMdmRgnCdAndMdmRgnNm  (String schGrpId,
                                                                       String schGrpRgnTpCd,
                                                                       String mdmRgnCd,
                                                                       String mdmRgnNm);
}
