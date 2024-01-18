package com.rockwell.scraping.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;

class CronJobServiceimplTest {

    @InjectMocks
    private CronJobServiceimpl cronJobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteScheduledTask() {
        // Arrange
        cronJobService.scheduleTask("https://www.example.com");

        // Act & Assert
        assertDoesNotThrow(() -> cronJobService.executeScheduledTask());
    }

    @Test
    void testValidateUrl_Success() {
        // Act & Assert
        assertDoesNotThrow(() -> cronJobService.validateUrl("https://www.example.com"));
    }

    @Test
    void testValidateUrl_Failure() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> cronJobService.validateUrl("not_a_url"));
    }
}

