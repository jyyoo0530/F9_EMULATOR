package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FMC_MSTR_CTRK_CGO_TP_MySqlRepository extends CrudRepository<FMC_MSTR_CTRK_CGO_TP, String> {
    List<FMC_MSTR_CTRK_CGO_TP> findBymstrCtrkNr (String masterContractNumber);
}
