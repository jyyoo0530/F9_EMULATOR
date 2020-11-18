package com.emulator.f9.model.market.mobility.sea.mdm;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface F9_MDM_LOCATION_ReactiveMongoRepository
        extends ReactiveMongoRepository<F9_MDM_LOCATION, String> {
    Mono<F9_MDM_LOCATION> findByLocationCode(String unLocCode);
}
