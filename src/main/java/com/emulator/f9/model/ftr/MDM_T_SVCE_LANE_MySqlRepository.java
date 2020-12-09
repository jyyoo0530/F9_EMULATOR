package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MDM_T_SVCE_LANE_MySqlRepository extends CrudRepository<MDM_T_SVCE_LANE, String> {
    List<MDM_T_SVCE_LANE> findByMdmSvcCd(String mdmSvcCd);
}
