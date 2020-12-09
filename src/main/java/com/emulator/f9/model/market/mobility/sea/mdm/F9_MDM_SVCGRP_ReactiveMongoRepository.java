package com.emulator.f9.model.market.mobility.sea.mdm;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Transactional
public interface F9_MDM_SVCGRP_ReactiveMongoRepository extends ReactiveMongoRepository<F9_MDM_SVCGRP, String> {
    Flux<F9_MDM_SVCGRP> findByServiceCode(String serviceCode);

}
