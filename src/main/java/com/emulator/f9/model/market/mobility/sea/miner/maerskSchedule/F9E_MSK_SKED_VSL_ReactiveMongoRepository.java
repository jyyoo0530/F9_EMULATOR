package com.emulator.f9.model.market.mobility.sea.miner.maerskSchedule;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Transactional
public interface F9E_MSK_SKED_VSL_ReactiveMongoRepository extends ReactiveMongoRepository<F9E_MSK_SKED_VSL, String> {
    Flux<F9E_MSK_SKED_VSL> findByScheduleKey(String scheduleKey);
}
