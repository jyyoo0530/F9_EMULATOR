package com.emulator.f9.model.tmp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MDM_T_PORT_MySqlRepository extends CrudRepository<MDM_T_PORT, Integer> {
}
