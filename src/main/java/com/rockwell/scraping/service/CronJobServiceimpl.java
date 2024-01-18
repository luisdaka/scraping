package com.rockwell.scraping.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
@Slf4j
public class CronJobServiceimpl implements CronJobService {

    @Value("${cronJobService.cronExpression}")
    private  String cronExpressions;
    private  String urls="https://www.google.com";

    @Override
    public void scheduleTask(String url) {
        log.info("Scheduling task with cron expression '{}' and URL '{}'",  cronExpressions, url);
        this.urls=url;
    }

    @Scheduled(cron = "${cronJobService.cronExpression}")
    public void executeScheduledTask() {
        validateUrl(urls);
        getHeaders(urls);
        getHtmlContent(urls);
    }

    public void getHeaders(String url) {
        try {
            Connection.Response response = Jsoup.connect(url).execute();
            Map<String, String> headers = response.headers();
            log.info("Headers");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                log.info("{}: {}", entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            log.error("Error getting Headers", e);
            throw new RuntimeException("Error getting Headers", e);

        }
    }

    public void getHtmlContent(String url ) {
        try {
            Document document = Jsoup.connect(url).get();
            String content = document.text().substring(0, Math.min(document.text().length(), 1000));
            log.info("Content: {}", content);

        } catch (IOException e) {
            log.error("Error getting HtmlContent", e);
        }
    }

    public void validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            log.error("(MalformedURL", e);
            throw new RuntimeException("(MalformedURL", e);
        }
    }
}