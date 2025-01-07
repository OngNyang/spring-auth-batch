package org.example.springauthbatch.Exception.handler;

import org.example.springauthbatch.Exception.UtilProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public ResponseEntity<String>   handleUtilProcessException(UtilProcessException ex) {
        logger.error("UtilProcessException occurred: {}", ex.getMessage());

        return (ResponseEntity.status(500).body(ex.getMessage()));
    }
}
