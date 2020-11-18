//package com.emulator.f9.database.configuration;
//
//import com.mongodb.reactivestreams.client.MongoClient;
//import com.mongodb.reactivestreams.client.MongoClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
//
//@EnableReactiveMongoRepositories
//public class MongoRepositoryConfiguration
//        extends AbstractReactiveMongoConfiguration {
//
//    @Bean
//    public MongoClient mongoClient() {
//        return MongoClients.create("mongodb://f9s:12345678@data.freight9.com:27017/f9s");
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return "f9s";
//    }
//}
