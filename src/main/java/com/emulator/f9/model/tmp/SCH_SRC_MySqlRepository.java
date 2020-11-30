package com.emulator.f9.model.tmp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SCH_SRC_MySqlRepository extends CrudRepository<SCH_SRC, Integer> {
}
