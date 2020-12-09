package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MDM_T_VSL_MySqlRepository extends CrudRepository<MDM_T_VSL, Integer> {
}
