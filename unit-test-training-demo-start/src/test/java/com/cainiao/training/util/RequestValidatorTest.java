package com.cainiao.training.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RequestValidatorTest {

    @Test
    void testValid() {
        boolean result = RequestValidator.valid("request");
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme