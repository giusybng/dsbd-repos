package com.bongiovannicomitini.loggingsystem.health;

import com.bongiovannicomitini.loggingsystem.errorhandling.ResourceNotFoundException;
import com.bongiovannicomitini.loggingsystem.model.Log;
import com.bongiovannicomitini.loggingsystem.repository.LogRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@EnableScheduling
public class Heartbeater {

    @Autowired
    LogRepository logRepository;

    @Value("http://payment:2222/ping")
    private String url;

    @Scheduled(fixedDelay = 1000)
    public void heartbeat(){
        RestTemplate restTemplate = new RestTemplate();
        HeartBeatBody jsonBody = new HeartBeatBody();
        jsonBody.setService("logging");
        jsonBody.setServiceStatus("up");

        //Check if mongodb is up
        int errorCode = check();
        if (errorCode != 1){
            jsonBody.setDbStatus("down");
        }
        else{
            jsonBody.setDbStatus("up");
        }

        System.out.println(new Gson().toJson(jsonBody));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(new Gson().toJson(jsonBody),headers);
        try {
            String answer = restTemplate.postForObject(url, entity, String.class);
            System.out.println(answer);
        }
        catch (ResourceAccessException e){
            System.out.println("Connection error to /ping");
        }
    }

    private int check(){
        List<Log> results = logRepository.findAll();
        return results.size();
    }

}
