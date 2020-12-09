package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SCH_GRP_DETAILS_MySqlRepository extends CrudRepository<SCH_GRP_DETAILS, String> {
}
