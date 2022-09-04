package com.natwest.assignment.controller;

import com.natwest.assignment.business.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Created by kunalkho on 9/3/2022.
 */

@RestController
public class Controller {

    public static Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    BusinessService businessService;

    @GetMapping(value = "/api/natwest/assignment/getprimenumbers")
    public ResponseEntity getPrimeNumbers(@RequestParam Integer number, @RequestParam Optional<String> algorithm, @RequestHeader(HttpHeaders.CONTENT_TYPE) Optional<String> contentType) throws Exception {
        Instant start = Instant.now();
        log.info("Inside Controller::getPrimeNumbers,start time = " + start);
        log.info("number = " + number);
        log.info("contentType = " + Optional.of(contentType));
        try {
            if (number < 0) {
                Instant end = Instant.now();
                log.info("Time taken(nanoseconds) = " + Duration.between(start, end).getNano());
                if (contentType.isPresent()) {
                    if (contentType.get().equalsIgnoreCase("application/xml"))
                        return new ResponseEntity<>(businessService.createErrorResponseXml(number, "Error : Input Element is negative number"), HttpStatus.BAD_REQUEST);
                    else
                        return new ResponseEntity<>(businessService.createErrorResponseJson(number, "Error : Input Element is negative number"), HttpStatus.BAD_REQUEST);
                } else
                    return new ResponseEntity<>(businessService.createErrorResponseJson(number, "Error : Input Element is negative number"), HttpStatus.BAD_REQUEST);
            }
            List<Integer> primeNumbersList = businessService.processNumber(number, algorithm);
            Instant end = Instant.now();
            log.info("Time taken(nanoseconds) = " + Duration.between(start, end).getNano());
            if (contentType.isPresent()) {
                if (contentType.get().equalsIgnoreCase("application/xml"))
                    return new ResponseEntity<>(businessService.createSuccessResponseXml(number, primeNumbersList), HttpStatus.OK);
                else
                    return new ResponseEntity<>(businessService.createSuccessResponseJson(number, primeNumbersList), HttpStatus.OK);
            } else
                return new ResponseEntity<>(businessService.createSuccessResponseJson(number, primeNumbersList), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error during processing request, error is " + e.getCause());
            if (contentType.isPresent()) {
                if (contentType.get().equalsIgnoreCase("application/xml"))
                    return new ResponseEntity<>(businessService.createErrorResponseXml(number, "Error during processing request, error is " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
                else
                    return new ResponseEntity<>(businessService.createErrorResponseJson(number, "Error during processing request, error is " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            } else
                return new ResponseEntity<>(businessService.createErrorResponseJson(number, "Error during processing request, error is " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
