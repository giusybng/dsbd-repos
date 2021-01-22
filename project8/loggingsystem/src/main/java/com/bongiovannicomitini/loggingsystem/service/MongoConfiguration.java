package com.bongiovannicomitini.loggingsystem.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(
        exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class,
                EmbeddedMongoAutoConfiguration.class
        }
)
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
@ComponentScan({"com.bongiovannicomitini", "com.bongiovannicomitini.loggingsystem"})
@EnableMongoRepositories ("com.bongiovannicomitini.loggingsystem") // this fix the problem
public class MongoConfiguration extends AbstractMongoClientConfiguration {
    @Value(value = "${MONGO_HOST}")
    private String mongoHost;

    @Value(value = "${MONGO_ROOT_USERNAME}")
    private String mongoUser;

    @Value(value = "${MONGO_ROOT_PASSWORD}")
    private String mongoPass;

    @Value(value = "${MONGO_AUTH_DB}")
    private String mongoAuthDB;

    @Value(value = "${MONGO_PORT}")
    private String mongoPort;

    @Value(value = "${MONGO_DB_NAME}")
    private String mongoDBName;

    public MongoConfiguration() {
    }

    @Override
    protected String getDatabaseName() {
        return this.mongoDBName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        String s = String.format("mongodb://%s:%s@%s:%s/%s?authSource=%s",
                mongoUser, mongoPass, mongoHost, mongoPort, mongoDBName, mongoAuthDB);
        return MongoClients.create(s);
    }
}