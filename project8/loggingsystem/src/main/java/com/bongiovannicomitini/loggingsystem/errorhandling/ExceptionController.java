package com.bongiovannicomitini.loggingsystem.errorhandling;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    @Value("logging")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String key, String msg) {
        kafkaTemplate.send(topicName, key, msg);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<HttpError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

        Map<String,Object> value = new HashMap<String,Object>();
        value.put("timestamp", System.currentTimeMillis());
        value.put("sourceIp", request.getRemoteAddr());
        value.put("service", "logging");
        value.put("request", request.getRequestURI() + " method GET");
        value.put("error", 404);

        sendMessage("http_errors", new Gson().toJson(value));

        HttpError responseMessage = new HttpError();
        responseMessage.setErrorMessage(ex.getMessage());
        responseMessage.setErrorCode("NOT_FOUND");
        responseMessage.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<HttpError>(responseMessage, HttpStatus.NOT_FOUND);
    }


}
