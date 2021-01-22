package com.bongiovannicomitini.loggingsystem.health;

public class HeartBeatBody {
    private String service;
    private String serviceStatus;
    private String dbStatus;

    public String getService() {
        return service;
    }

    public HeartBeatBody setService(String service) {
        this.service = service;
        return this;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public HeartBeatBody setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
        return this;
    }

    public String getDbStatus() {
        return dbStatus;
    }

    public HeartBeatBody setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
        return this;
    }
}
