package com.emulator.f9.model.market.mobility.sea.mdm;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface F9_MDM_DATAOWNER_ReactiveMongoRepository
        extends ReactiveMongoRepository<F9_MDM_DATAOWNER, String> {
}
