package com.emulator.f9.database.configuration;


import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Autowired
    MongoClient mongoClient;

    @Bean
    public MongoTemplate MongoTemplate() {
        return new MongoTemplate(mongoClient, "f9s");
    }

}
