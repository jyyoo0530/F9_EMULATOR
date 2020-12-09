package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FMC_MSTR_CTRK_LINE_ITEM_MySqlRepository extends CrudRepository<FMC_MSTR_CTRK_LINE_ITEM, String> {
    List<FMC_MSTR_CTRK_LINE_ITEM> findBymstrCtrkNr (String masterContractNumber);
}
