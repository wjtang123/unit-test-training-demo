package com.cainiao.training.infra;

import com.cainiao.training.dto.DemoDTO;
import com.cainiao.training.util.DemoLandlordClient;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

// mockito不支持的方法有4种：静态、私有、构造、final
// 对于mockito不支持的方法，可以用PowerMockito;
// PowerMockito只支持JUnit4；JUnit5不行；
// 对于PowerMockito的使用，需要加两个注解（在类上）
// 而且还需要在测试方法里，重新用PowerMockito.mock或spy一次；
@RunWith(PowerMockRunner.class)         // 代表该测试类使用PowerMock
@PrepareForTest({DemoLandlordClient.class, DemoTairClient.class, DemoDTO.class}) // 告诉PowerMock哪些类需要字节码级别操作
public class DemoTairClientTest {


    @Mock
    DemoLandlordClient demoLandlordClient;

    @InjectMocks
    DemoTairClient demoTairClient;

    // 静态方法
    @Test
    public void testGetCache() {
        // 1. mock静态类
        PowerMockito.mockStatic(DemoLandlordClient.class);
        // 2. 定义静态方法的返回值
        PowerMockito.when(DemoLandlordClient.getTenantId()).thenReturn("Redefined answer");
        // 3. 调用静态方法
        String result = demoTairClient.getCache("key");
        // 4. 校验运行结果
        assertThat(result).contains("Redefined");
        // 5. verify静态方法；这里有点特殊，verifyStatic之后还需要再调用一次静态方法
        PowerMockito.verifyStatic(DemoLandlordClient.class, times(1));
        DemoLandlordClient.getTenantId();
    }

    // 私有方法
    @Test
    public void testDoBatchGet() throws Exception {
        // 如果要测mockito不支持的方法（4种），需要在测试方法内重新用PowerMockito.mock或spy一次；
        // 1. 模拟私有方法所在类
        DemoTairClient demoTairClient = PowerMockito.spy(new DemoTairClient());
        List<String> list = new ArrayList<>();
        list.add("k1");
        list.add("k2");
        List<String> list1 = new ArrayList<>();
        list1.add("b1");
        // 2. 定义私有方法的返回值
        PowerMockito.doReturn(list1).when(demoTairClient, "doBatchGet", anyList());
        // 3. 调用私有方法
        List<String> result = demoTairClient.batchGet(list);
        // 4. 校验运行结果
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0), "b1");
        // 5. verify私有方法
        PowerMockito.verifyPrivate(demoTairClient, times(1)).invoke("doBatchGet", anyList());
    }
    //    DemoDTO demoDTO = new DemoDTO();
//    @Before
//    public void before() {
//        demoDTO.setName("xiaowang");
//        demoDTO.setPassword("pwd_xiaowang");
//    }
    // 构造方法
    @Test
    public void testConstructDemoDTO() throws Exception {
        DemoDTO demoDTO = new DemoDTO();
        demoDTO.setName("xiaowang");
        demoDTO.setPassword("pwd_xiaowang");
        // 1. 定义构造方法的返回值
        PowerMockito.whenNew(DemoDTO.class).withNoArguments().thenReturn(demoDTO);
        // 2. 调用构造方法
        DemoDTO testDTO = demoTairClient.constructDemoDTO();
        // 3. 校验运行结果
        Assertions.assertEquals(testDTO.getName(), "xiaowang");
        // 4. verify构造方法
        PowerMockito.verifyNew(DemoDTO.class, times(1)).withNoArguments();
    }

    // 1. 模拟final方法所在类
    @Mock
    DemoDTO demoDTO;

    // final方法
    @Test
    public void testGetFinalMethod() {
        // 2. 定义final方法返回值
        PowerMockito.when(demoDTO.getFinalValue()).thenReturn("Redefined final value");
        // 3. 调用final方法
        String result = demoTairClient.getFinalMethod();
        // 4. 校验运行结果
        Assertions.assertEquals("Redefined final value", result);
        // 5. verify final方法
        verify(demoDTO, times(1)).getFinalValue();
        verifyNoMoreInteractions(demoDTO);
    }
}
