package com.cainiao.training.service;

import com.cainiao.training.infra.DemoDBMapper;
import com.cainiao.training.infra.DemoTairClient;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class BDemoServiceTest {
    @Mock
    DemoTairClient mockTairClient;  // 1.2 模拟依赖对象
    @Mock
    DemoDBMapper mockDBMapper;
    @InjectMocks                   // 1.3 注入依赖对象
    DemoService demoService;       // 1.1 定义测试对象

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetResult_succeed_getFromCache() throws IOException {
        when(mockTairClient.getCache(Mockito.eq("request"))).thenReturn("getCacheResponse");     // 2.2 模拟依赖对象（参数、返回值和异常） 【注】：此处被模拟的参数为anyString()，返回值为"getCacheResponse"；
                                                                                          // 2.4 模拟依赖方法 【注】：此处被模拟的方法为mockTairClient.getCache(anyString())
        String result = demoService.getResult("request");                         // 3.5 调用测试方法
        Assert.assertEquals("getCacheResponse", result);                         // 3.7 验证数据对象（返回值和异常）
        verify(mockTairClient, times(1)).getCache(anyString());   // 4.6 验证依赖方法 【注】：验证调用次数，参数等
        verifyNoMoreInteractions(mockTairClient);                                        // 4.8 验证依赖对象 【注】：验证依赖对象的执行路径，例如，这里就是保证mockTairClient不会再有其他的调用
        verifyNoInteractions(mockDBMapper);                                              // 4.8 验证依赖对象 【注】：验证依赖对象的执行路径，例如，这里就是保证mockDBMapper没有任何调用
    }

    @Test
    //TODO void 返回如何断言 -- Argument Captor 的使用
    void testDeleteByKey_succeed_givenValidKey() {
        System.out.println("starting test now!");
        Mockito.doCallRealMethod().when(mockDBMapper).deleteData(anyString());
        // 定义ArgumentCaptor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        // 先调用，demoService.deleteByKey("KEY")里面会调用到mockDBMapper.deleteData();
        demoService.deleteByKey("KEY");
        // 再获取参数， 参数 = Delete from table_name where key = KEY
        verify(mockDBMapper).deleteData(argumentCaptor.capture());
        // 验证参数
        String actual = argumentCaptor.getValue();
        assertThat(actual).contains("Delete from", "KEY");

        verifyNoMoreInteractions(mockDBMapper);
        verifyNoInteractions(mockTairClient);

    }

    @Test
    //TODO 依赖里面的 Lambda 表达式内的逻辑怎么执行？
    public void testGetResults_succeed_getFromDB() {
        List<String> keys = Collections.nCopies(2, "KEY");

        doAnswer((Answer<List<String>>) invocationOnMock -> {
            Supplier<List<String>> loader = (Supplier<List<String>>) invocationOnMock.getArguments()[1];
            List dataFromSource = loader.get();
            return new ArrayList<>(dataFromSource);
        }).when(mockTairClient)
                .batchGet(any(), any());

        when(mockDBMapper.queryData(anyList())).thenReturn(Collections.singletonList("EXPECTED"));

        List<String> actuals = demoService.getResults(keys);

        assertThat(actuals).containsExactly("EXPECTED");

        verify(mockTairClient).batchGet(any(), any());
        verify(mockDBMapper).queryData(anyList());
        verifyNoMoreInteractions(mockTairClient, mockDBMapper);


    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme