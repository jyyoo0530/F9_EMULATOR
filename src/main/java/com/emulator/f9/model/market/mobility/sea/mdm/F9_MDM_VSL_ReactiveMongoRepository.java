package com.emulator.f9.model.market.mobility.sea.mdm;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface F9_MDM_VSL_ReactiveMongoRepository
        extends ReactiveCrudRepository<F9_MDM_VSL, String> {
    Mono<F9_MDM_VSL> findByVesselCode(String vesselCode);
}
