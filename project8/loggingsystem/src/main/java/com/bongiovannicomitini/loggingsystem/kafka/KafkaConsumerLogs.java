package com.bongiovannicomitini.loggingsystem.kafka;

import com.bongiovannicomitini.loggingsystem.model.Log;
import com.bongiovannicomitini.loggingsystem.service.LogService;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumerLogs {
    @Autowired
    LogService logService;

    @KafkaListener(topics = "logging", groupId = "logs-consumer")
    public void listenLogTopic(ConsumerRecord<String, String> record){
        String message = record.value();
        String key = record.key();
        System.out.println(key);

        if(message != null && key != null){
            Map<String, Object> value = new Gson().fromJson(message, HashMap.class);
            Log log = new Log(key, value);
            //Check if the timestamp exists in the log
            //If it doesn't exist, I add it
            if(log.getValue().containsKey("timestamp")){
            }
            else{
                log.getValue().put("timestamp",System.currentTimeMillis());
            }
            logService.addLog(log);
        }
    }
}
