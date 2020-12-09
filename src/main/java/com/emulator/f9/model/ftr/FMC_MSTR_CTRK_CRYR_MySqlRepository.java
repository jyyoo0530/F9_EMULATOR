package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FMC_MSTR_CTRK_CRYR_MySqlRepository extends CrudRepository<FMC_MSTR_CTRK_CRYR, String> {
    List<FMC_MSTR_CTRK_CRYR> findBymstrCtrkNr (String masterContractNumber);
}
