package com.bongiovannicomitini.loggingsystem.errorhandling;

import javax.servlet.http.HttpServletRequest;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);

    }

}
