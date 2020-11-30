package com.emulator.f9.model.market.mobility.sea.mdm;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface F9_MDM_LOCATION_ReactiveMongoRepository
        extends ReactiveMongoRepository<F9_MDM_LOCATION, String> {
    Mono<F9_MDM_LOCATION> findByLocationCode(String locationCode);
    Mono<F9_MDM_LOCATION> findByMdmOwnerLocationId(String mdmOwnerLocationId);
}
