package com.emulator.f9.model.ftr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MDM_T_PORT_MySqlRepository extends CrudRepository<MDM_T_PORT, Integer> {
    List<MDM_T_PORT> findByLocationStatus (String locationStatus);
    MDM_T_PORT findByMdmOwnerCodeAndLocationCode (String mdmOwnerCode, String locationCode);
    MDM_T_PORT findByMdmOwnerCodeAndCountryCodeAndLocationName (String mdmOwnerCode, String countryCode, String locationName);
    List<MDM_T_PORT> findByF9LocationId(String f9LocationId);
}
