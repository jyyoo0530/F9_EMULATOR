package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SCH_GRP_RTE_MySqlRepository extends CrudRepository<SCH_GRP_RTE, String> {
    List<SCH_GRP_RTE> findBySchGrpIdAndSchRteLocTpCd(String schGrpId, String schRteLocTpCd);
}
