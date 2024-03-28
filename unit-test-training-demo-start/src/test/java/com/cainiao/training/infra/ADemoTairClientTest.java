package com.cainiao.training.infra;

import com.cainiao.training.util.DemoLandlordClient;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DemoLandlordClient.class)
public class ADemoTairClientTest {
    @InjectMocks
    DemoTairClient demoTairClient;
    @Before
    public void setup() {
        PowerMockito.mockStatic(DemoLandlordClient.class);
        PowerMockito.when(DemoLandlordClient.getTenantId()).thenReturn("TAOBAO_HK");
        PowerMockito.when(DemoLandlordClient.getSecondTenantId(anyString())).thenReturn("abc");
    }
    @Test
    public void testGetCache() throws Exception {
        String actual = demoTairClient.getCache("key");
        Assertions.assertThat(actual).isEqualTo("key TAOBAO_HK");
        // 不建议对静态方法verify，至少目前没搞懂这玩意怎么运作的
        // 验证的是前面的调用次数，后面还要调用一次静态方法（times统计进去的那个方法，其他的不行，而且只需要一次，不管times多少），且只验证了DemoLandlordClient.getTenantId()，其他方法不验证，为啥？
        PowerMockito.verifyStatic(DemoLandlordClient.class, times(1));
        DemoLandlordClient.getTenantId();
    }
}