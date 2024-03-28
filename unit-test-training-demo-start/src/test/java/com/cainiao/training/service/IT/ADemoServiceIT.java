package com.cainiao.training.service.IT;

import com.cainiao.training.infra.DemoDBMapper;
import com.cainiao.training.infra.DemoTairClient;
import com.cainiao.training.service.DemoService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author tangweijie
 * @date 2024/3/18
 */

public class ADemoServiceIT {
    @Mock
    DemoTairClient mockTairClient;
    @Mock
    DemoDBMapper mockDBMapper;
    @InjectMocks
    DemoService demoService;

    @Test
    public void testGetResult_succeed_IT() throws Exception {

        System.out.println("这是集成测试用例！");
    }
}
