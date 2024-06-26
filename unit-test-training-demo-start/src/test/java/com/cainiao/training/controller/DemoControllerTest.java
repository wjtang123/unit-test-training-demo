package com.cainiao.training.controller;

import com.cainiao.training.service.DemoService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class DemoControllerTest {
    @Mock
    DemoService mockDemoService;
    @InjectMocks
    DemoController demoController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    //TODO 稍复杂 Service/Controller 层
    public void testExecute() throws Exception {

    }

    @Test
    //TODO 如何测试 Exception
    public void testExecution_throwsException_whenServiceThrowsException() throws Exception {
        when(mockDemoService.getResult(anyString())).thenThrow(new IOException("ex_message"));
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> demoController.execute("argument"))
                .withCauseExactlyInstanceOf(IOException.class)
                .withMessageContaining("ex_message");

        verify(mockDemoService).getResult(anyString());
        verifyNoMoreInteractions(mockDemoService);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme