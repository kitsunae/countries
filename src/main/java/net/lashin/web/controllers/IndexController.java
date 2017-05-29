package net.lashin.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage(){
        LOGGER.debug("Accessing index page");
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public ResponseEntity<Object> getDateTime() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.DATE, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return ResponseEntity.ok().headers(headers).build();
    }
}
