package net.lashin.web.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class WideExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(WideExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUncaughtException(Exception e) {
        LOG.error("Uncaught exception: ", e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
