package com.emulator.f9.model.market.mobility.sea;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface F9_SEA_SKD_ReactiveMongoRepository
        extends ReactiveMongoRepository<F9_SEA_SKD, String> {
    Flux<F9_SEA_SKD> findByServiceLaneName(String serviceLaneName);
    Flux<F9_SEA_SKD> findByServiceLaneKeyAndVoyageNumberAndVesselKeyAndFromKeyAndToKey(
            String serviceLane,
            String voyageNUmber,
            String vesselKey,
            String fromKey,
            String toKey
    );
}

