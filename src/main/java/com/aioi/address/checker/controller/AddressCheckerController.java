package com.aioi.address.checker.controller;

import com.aioi.address.checker.dto.AddressResponseDto;
import com.aioi.address.checker.service.AddressCheckerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AddressCheckerController {

    @Autowired
    private AddressCheckerService addressCheckerService;

    private static final Logger logger = LoggerFactory.getLogger(AddressCheckerController.class);

    @GetMapping("/services/addresses")
    ResponseEntity<?> getAddressesByQuery(@RequestParam("qtext") String searchQueryText){
        long startTime = System.currentTimeMillis();
        AddressResponseDto addressResponseDto = addressCheckerService.getAddressesByQuery(searchQueryText);
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        logger.info("GetAddressesByQuery Response Status={} | timeTaken={}", addressResponseDto.getStatus(), timeTaken);
        return new ResponseEntity<>(addressResponseDto, HttpStatus.OK);
    }
}
