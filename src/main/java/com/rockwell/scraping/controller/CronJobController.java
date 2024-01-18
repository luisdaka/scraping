package com.rockwell.scraping.controller;

import com.rockwell.scraping.service.CronJobService;
import com.rockwell.scraping.model.CronJobRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cronjob")
@RequiredArgsConstructor
public class CronJobController {

    @Autowired
    CronJobService cronJobService;

    @PostMapping("/schedule")
    @CrossOrigin
    public ResponseEntity<String> scheduleTask(@RequestBody CronJobRequest cronJobRequest) {

        try {
            cronJobService.scheduleTask(cronJobRequest.getUrl());
            return new ResponseEntity<>("result successful result",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
