package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FMC_MSTR_CTRK_MySqlRepository extends CrudRepository<FMC_MSTR_CTRK, String> {
}
