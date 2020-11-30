package com.emulator.f9.model.market.mobility.sea;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface F9_SEA_SKD_ReactiveMongoRepository
        extends ReactiveMongoRepository<F9_SEA_SKD, String> {
}
