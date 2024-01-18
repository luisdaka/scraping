package com.rockwell.scraping.model;

import lombok.Data;

@Data
public class CronJobRequest {

    private String cronExpression;
    private String url;

}
