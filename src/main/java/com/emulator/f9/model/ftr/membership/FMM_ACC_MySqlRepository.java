package com.emulator.f9.model.ftr.membership;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FMM_ACC_MySqlRepository extends CrudRepository<FMM_ACC, String> {
    FMM_ACC findByAccOwnCoCd (String user);
}
