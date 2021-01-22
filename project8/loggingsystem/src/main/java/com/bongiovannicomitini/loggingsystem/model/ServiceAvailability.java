package com.bongiovannicomitini.loggingsystem.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ServiceAvailability {
    private Double serviceAvailability;
    private Double serverAvailability;
    private Double dbAvailability;

    @JsonCreator
    public ServiceAvailability(Double serviceAvailability, Double serverAvailability, Double dbAvailability) {
        this.serviceAvailability = serviceAvailability;
        this.serverAvailability = serverAvailability;
        this.dbAvailability = dbAvailability;
    }

    @JsonCreator
    public ServiceAvailability() {
    }

    public Double getServiceAvailability() {
        return serviceAvailability;
    }

    public void setServiceAvailability(Double serviceAvailability) {
        this.serviceAvailability = serviceAvailability;
    }

    public Double getServerAvailability() {
        return serverAvailability;
    }

    public void setServerAvailability(Double serverAvailability) {
        this.serverAvailability = serverAvailability;
    }

    public Double getDbAvailability() {
        return dbAvailability;
    }

    public void setDbAvailability(Double dbAvailability) {
        this.dbAvailability = dbAvailability;
    }

    @Override
    public String toString() {
        return "ServiceAvailability{" +
                "serviceAvailability=" + serviceAvailability +
                ", serverAvailability=" + serverAvailability +
                ", dbAvailability=" + dbAvailability +
                '}';
    }
}
