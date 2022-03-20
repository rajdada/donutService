package com.shop.services.mycart.task;

import com.shop.services.mycart.Util;
import com.shop.services.mycart.dao.OrderRepository;
import com.shop.services.mycart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersPullingTaskTest
{
    @SpyBean
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @SpyBean
    OrdersPullingTask ordersPullingTask;

    @Test
    public void scheduleTask()
    {
        //given
        when(orderRepository.fetchOrdersBySubmission()).thenReturn(Util.expectedOrders());

        //when
        ordersPullingTask.scheduleTask();

        //then
        verify(orderService).fetchOrdersBySubmission();
    }

}
