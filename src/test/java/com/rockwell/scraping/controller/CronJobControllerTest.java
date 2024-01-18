package com.rockwell.scraping.controller;

import com.rockwell.scraping.controller.CronJobController;
import com.rockwell.scraping.service.CronJobService;
import com.rockwell.scraping.model.CronJobRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CronJobControllerTest {

    @Mock
    private CronJobService cronJobService;

    @InjectMocks
    private CronJobController cronJobController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testScheduleTask_Success() {
        // Arrange
        CronJobRequest cronJobRequest = new CronJobRequest();
        cronJobRequest.setUrl("https://www.callefalsa.com");

        // Mocking the behavior of cronJobService.scheduleTask
        doNothing().when(cronJobService).scheduleTask(any());

        // Act
        ResponseEntity<String> response = cronJobController.scheduleTask(cronJobRequest);

        // Assert
        verify(cronJobService, times(1)).scheduleTask(cronJobRequest.getUrl());
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals("result successful result"));
    }

    @Test
    void testScheduleTask_Failure() {
        // Arrange
        CronJobRequest cronJobRequest = new CronJobRequest();
        cronJobRequest.setUrl("https://www.callefalsa.com");

        // Mocking the behavior of cronJobService.scheduleTask to throw an exception
        doThrow(new RuntimeException("Test Exception")).when(cronJobService).scheduleTask(any());

        // Act
        ResponseEntity<String> response = cronJobController.scheduleTask(cronJobRequest);

        // Assert
        verify(cronJobService, times(1)).scheduleTask(cronJobRequest.getUrl());
        assert(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
        assert(response.getBody().equals("Test Exception"));
    }
}
