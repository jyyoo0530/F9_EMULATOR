package com.emulator.f9.model.market.mobility.sea.mdm;

import com.emulator.f9.model.ftr.MDM_T_PORT;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
public interface F9_MDM_LOCATION_ReactiveMongoRepository
        extends ReactiveMongoRepository<F9_MDM_LOCATION, String> {
    Flux<F9_MDM_LOCATION> findByLocationCode(String locationCode);
    Mono<F9_MDM_LOCATION> findByMdmOwnerLocationId(String mdmOwnerLocationId);
    Flux<F9_MDM_LOCATION> findByLocationCodeAndMdmOwnerCode(String locationCode, String mdmOwnerCode);
    Mono<F9_MDM_LOCATION> findByMdmOwnerCodeAndLocationCode (String mdmOwnerCode, String locationCode);
    Mono<F9_MDM_LOCATION> findByMdmOwnerCodeAndCountryCodeAndLocationName (String mdmOwnerCode, String countryCode, String locationName);
    Flux<F9_MDM_LOCATION> deleteAllByMdmOwnerCodeAndRegionCode (String mdmOwnerCode, String regionCode);
}
