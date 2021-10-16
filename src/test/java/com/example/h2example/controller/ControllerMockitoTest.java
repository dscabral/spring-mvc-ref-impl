package com.example.h2example.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.h2example.service.LiveTestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ControllerMockitoTest {

    @Mock
    private LiveTestService liveTestService;
    @InjectMocks
    private LiveTestController liveTestController;

    @BeforeEach
    public void setUp() {
        when(liveTestService.liveTest()).thenReturn("UP");
    }

    @Test
    public void shouldReturnDefaultUPMessage() {
        String response = liveTestController.liveTest();
        assertThat(response).isEqualTo("UP");
    }

}
