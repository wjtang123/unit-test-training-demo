package com.cainiao.training.service;

import com.alibaba.fastjson.JSON;
import com.cainiao.training.dto.DemoDTO;
import com.cainiao.training.infra.DemoDBMapper;
import com.cainiao.training.infra.DemoTairClient;
import com.cainiao.training.service.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DemoServiceTest {
    @Mock
    DemoTairClient tairClient;
    @Mock
    DemoDBMapper dbMapper;
    @InjectMocks
    DemoService demoService;

    @Test
    //TODO 稍复杂 Service/Controller 层
    public void testGetResult_succeed_getFromCache() throws Exception {
        String demoDTOJson = "{\"name\":\"testUser\",\"password\":\"fjalj12348>*\"}";
        DemoDTO demoDTO = JSON.parseObject(demoDTOJson, DemoDTO.class);
        System.out.println(demoDTO.getName());
        System.out.println(demoDTO.getPassword());
        Assert.assertEquals("用户名：", demoDTO.getName(), "testUser");
        Assert.assertEquals("密码：", demoDTO.getPassword(), "fjalj12348>*");
    }

    @Test
    //TODO void 返回如何断言 -- Argument Captor 的使用
    public void testDeleteByKey_succeed_givenValidKey() {


    }

    @Test
    //TODO 依赖里面的 Lambda 表达式内的逻辑怎么执行？
    public void testGetResults_succeed_getFromDB(){



    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme