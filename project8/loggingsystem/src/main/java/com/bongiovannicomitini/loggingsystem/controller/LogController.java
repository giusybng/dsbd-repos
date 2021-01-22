package com.bongiovannicomitini.loggingsystem.controller;

import com.bongiovannicomitini.loggingsystem.errorhandling.ResourceNotFoundException;
import com.bongiovannicomitini.loggingsystem.model.Log;
import com.bongiovannicomitini.loggingsystem.model.ServiceAvailability;
import com.bongiovannicomitini.loggingsystem.repository.LogRepository;
import com.bongiovannicomitini.loggingsystem.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Component
public class LogController {

    @Autowired
    LogRepository logRepository;

    @Autowired
    LogService logService;

    //GET http://localhost:8080/all
    @GetMapping(path = "/all")
    public @ResponseBody List<Log> getAllLogs(){
        return logRepository.findAll();
    }

    //GET http://localhost:8080/deleteAll
    @GetMapping(path = "/deleteAll")
    public @ResponseBody void deleteAllLogs(){
        logRepository.deleteAll();
    }

    //GET /keys/{key}?from=unixTimestampStart&end=unixTimestampEnd retrieve di tutti i log message con key = {key} che abbiano tempo
    //compreso fra i due forniti nei parametri della GET (obbligatori)
    @RequestMapping(value="/keys/{key}", method = RequestMethod.GET)
    public @ResponseBody List<Log> retrieveLogsTimestamp(@PathVariable("key") String key,
                                           @RequestParam("from") long unixTimestampStart,
                                           @RequestParam("end") long unixTimestampEnd){

        //Check if there are any log messages with that key
        List<Log> checkLogsKey = logRepository.findByKey(key);
        if(checkLogsKey.isEmpty()) throw new ResourceNotFoundException("There are no logs with that key.");

        //Search for all logs with timestamp between unixTimestampStart and unixTimestampEnd
        List<Log> logs = logService.retrieveLogsTimestamp(key, unixTimestampStart, unixTimestampEnd);
        if(logs.isEmpty()) throw new ResourceNotFoundException("There are no logs in that timestamp range.");

        return logs;
    }

    //GET /http_errors/services/{service}?from=unixTimestampStart&end=unixTimestampEnd : retrieve di tutti i log message di tipo
    //http_errors associati al service {service}
    @RequestMapping(value="/http_errors/services/{service}", method = RequestMethod.GET)
    public @ResponseBody List<Log> retrieveLogsHttpErrors(@PathVariable("service") String service,
                                                @RequestParam("from") long unixTimestampStart,
                                                @RequestParam("end") long unixTimestampEnd){

        //Check if there are any log messages with key : 'http_errors' and service : service
        List<Log> checkLogsService = logRepository.findByService("http_errors", service);
        if(checkLogsService.isEmpty()) throw new ResourceNotFoundException("There are no logs with key : 'http_errors' of that service.");

        List<Log> logs = logService.retrieveLogsHttpErrors(service, unixTimestampStart, unixTimestampEnd);
        if(logs.isEmpty()) throw new ResourceNotFoundException("There are no logs in that timestamp range.");

        return logs;
    }

    //GET /uptime/services/{service}?from=unixTimeStampStart&end=unixTimestampEnd risponde con informazioni sull'availability del
    //service {service}. Ovvero,ricerca tutti i messaggi service_down associati al service {service},li considera validi per 30 secondi, li conta,
    //raggruppati per status e ritorn un json con le availability: (specific_status_count * 30 / deltaTime)
    @RequestMapping(value="/uptime/services/{service}", method = RequestMethod.GET)
    public @ResponseBody ServiceAvailability retrieveLogsAvailability(@PathVariable("service") String service,
                                                          @RequestParam("from") long unixTimestampStart,
                                                          @RequestParam("end") long unixTimestampEnd){

        //Check if unixTimestampStart e unixTimestampEnd differ by 86400s
        if(unixTimestampEnd-unixTimestampStart!=86400) throw new ResourceNotFoundException("The timestamp range is invalid. Start and End must differ by exactly one day (86400 seconds).");

        //Check if there are any log messages with key : 'service_down' and service : service
        List<Log> checkLogsService = logRepository.findByService("service_down", service);
        if(checkLogsService.isEmpty()) throw new ResourceNotFoundException("There are no logs with key : 'service_down' of that service.");

        ServiceAvailability availability = logService.retrieveLogsAvailability(service, unixTimestampStart, unixTimestampEnd);
        System.out.println(availability.toString());
        return availability;
    }

}