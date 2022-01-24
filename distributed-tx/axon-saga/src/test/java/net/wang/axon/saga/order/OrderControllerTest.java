package net.wang.axon.saga.order;

import net.wang.axon.saga.AxonSagaApplicationTests;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.MockitoCore;

import javax.annotation.Resource;

public class OrderControllerTest extends AxonSagaApplicationTests {

    @Resource
     OrderController orderController;
    @Test
    public void create() {
    }
}