package com.emulator.f9.model.market.mobility.sea.mdm;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface F9_SEA_SKD_GRP_ReactiveMongoRepository extends ReactiveMongoRepository<F9_SEA_SKD_GRP, String> {
    Mono<F9_SEA_SKD_GRP> findByServiceCodeAndServiceDirection(String serviceCode, String serviceDirection);
}
