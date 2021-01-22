package com.bongiovannicomitini.loggingsystem.service;

import com.bongiovannicomitini.loggingsystem.model.Log;
import com.bongiovannicomitini.loggingsystem.model.ServiceAvailability;
import com.bongiovannicomitini.loggingsystem.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    //Add log in mongodb
    public void addLog(Log log){
        logRepository.save(log);
        System.out.println("Log saved in mongodb!");
    }

    //search logs through key with timestamp in range unixTimestampStart and unixTimestampEnd
    public List<Log> retrieveLogsTimestamp(String key, long unixTimestampStart, long unixTimestampEnd){
        List<Log> logs = logRepository.findLogsByKeyAndValue(key, unixTimestampStart, unixTimestampEnd);
        return logs;
    }

    //search logs through key and service with timestamp in range unixTimestampStart and unixTimestampEnd
    public List<Log> retrieveLogsHttpErrors(String service, long unixTimestampStart, long unixTimestampEnd){
        String key = "http_errors";
        List<Log> logs = logRepository.findLogsByKeyAndValueAndService(key, service, unixTimestampStart, unixTimestampEnd);
        return logs;
    }

    //search logs through key and service with timestamp in range unixTimestampStart and unixTimestampEnd and count service_down message
    public ServiceAvailability retrieveLogsAvailability(String service, long unixTimestampStart, long unixTimestampEnd){
        String key = "service_down";
        String status = "down";
        Double serviceDown = 0.0;
        Double dbStatus = 0.0;
        Double serverUnavailable = 0.0;
        Double serviceAvailability;
        Double serverAvailability;
        Double dbAvailability;

        //group logs by status
        List<Log> logs1 = logRepository.findLogsByKeyAndValueAndServiceAndDbStatus(key, service, status, unixTimestampStart, unixTimestampEnd);
        List<Log> logs2 = logRepository.findLogsByKeyAndValueAndServiceAndServerStatus(key, service, unixTimestampStart, unixTimestampEnd);
        List<Log> logs3 = logRepository.findLogsByKeyAndValueAndServiceAndServiceStatus(key, service, status, unixTimestampStart, unixTimestampEnd);

        //count
        for(Log l: logs1){
            dbStatus++;
            System.out.println(dbStatus);
        }
        for(Log l: logs2){
            serverUnavailable++;
            System.out.println(serverUnavailable);
        }
        for(Log l: logs3){
            serviceDown++;
            System.out.println(serviceDown);
        }

        //calculate availability
        serviceAvailability = (1.0-serviceDown*30.0/86400.0);
        serverAvailability = (1.0-serverUnavailable*30.0/86400.0);
        dbAvailability = (1.0-dbStatus*30.0/86400.0);

        ServiceAvailability availability = new ServiceAvailability(serviceAvailability, serverAvailability, dbAvailability);
        return availability;
    }
}
