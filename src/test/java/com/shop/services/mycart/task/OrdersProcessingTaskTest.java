package com.shop.services.mycart.task;

import com.shop.services.mycart.Util;
import com.shop.services.mycart.dao.CustomerRepository;
import com.shop.services.mycart.dao.OrderRepository;
import com.shop.services.mycart.model.Order;
import com.shop.services.mycart.service.CustomerService;
import com.shop.services.mycart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersProcessingTaskTest
{
    @SpyBean
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    CustomerService customerService;

    @SpyBean
    OrdersProcessingTask processingTask;

    @Test
    public void scheduleTask() throws Exception
    {
        //given
        List<Order> orders = Util.expectedNextDeliveries();
        Util.setFinalStatic(OrderService.class.getDeclaredField("orderQueue"), orders);
        when(orderRepository.deleteOrder(anyInt())).thenReturn(1);
        doNothing().when(customerService).deleteCustomer(anyInt());

        //when
        processingTask.scheduleTask();

        //then
        verify(orderService, times(3)).deleteOrderByCustomerId(anyInt());
        verify(orderService, times(3)).clearOrderAfterProcess(any(Order.class));
    }


}
