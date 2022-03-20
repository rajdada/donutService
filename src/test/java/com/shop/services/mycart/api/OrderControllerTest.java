package com.shop.services.mycart.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.services.mycart.dto.OrderDTO;
import com.shop.services.mycart.model.Customer;
import com.shop.services.mycart.model.Order;
import com.shop.services.mycart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private static final long createTime = new Date().getTime();

    @Test
    public void addOrder() throws Exception
    {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(1);
        orderDTO.setQuantity(10);

        //given
        Customer customer = new Customer(1);
        Order o1 = new Order(createTime, 10, customer);
        o1.setOrderId(1);

        when(orderService.addOrder(any(Order.class))).thenReturn(o1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(orderDTO);

        final String url = "/addOrder";

        //when
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is("01")));

        //then
        verify(orderService).addOrder(any(Order.class));
    }

    @Test
    public void getQueuePositionAndWaitTime() throws Exception
    {
        //given
        Customer customer = new Customer(1);
        Order o1 = new Order(createTime, 10, customer);
        o1.setOrderId(1);

        when(orderService.getQueuePositionAndWaitTime(anyInt())).thenReturn(o1);

        final String url = "/getQueuePositionAndWaitTime/1";

        //when
        mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.customerId", is(1)));

        //then
        verify(orderService).getQueuePositionAndWaitTime(anyInt());
    }

    @Test
    public void getAllOrdersAndWaitTime() throws Exception
    {
        //given
        List<Order> orders = expectedOrders();

        when(orderService.getAllOrdersAndWaitTime()).thenReturn(orders);

        final String url = "/getAllOrdersAndWaitTime";

        //when
        mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].customer.custId", is(1)));

        //then
        verify(orderService).getAllOrdersAndWaitTime();
    }

    @Test
    public void nextDelivery() throws Exception
    {
        //given
        Customer customer = new Customer(1);
        Order o1 = new Order(createTime, 10, customer);
        o1.setOrderId(1);
        List<Order> orders = new LinkedList<>();
        orders.add(o1);

        when(orderService.nextDelivery()).thenReturn(orders);

        final String url = "/nextDelivery";

        //when
        mockMvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].customerId", is(1)));

        //then
        verify(orderService).nextDelivery();
    }

    @Test
    public void deleteOrder() throws Exception
    {
        //given
        when(orderService.deleteOrderByCustomerId(anyInt())).thenReturn(1);

        final String url = "/deleteOrder/1";

        //when
        mockMvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("02")));

        //then
        verify(orderService).deleteOrderByCustomerId(anyInt());
    }

    List<Order> expectedOrders()
    {
        Customer c1 = new Customer(1);
        Order o1 = new Order(createTime, 10, c1);
        o1.setWaitTime(1)
                .setPosition(1)
                .setOrderId(1);

        Customer c2 = new Customer(2);
        Order o2 = new Order(createTime, 10, c2);
        o2.setWaitTime(2)
                .setPosition(2)
                .setOrderId(2);

        Customer c3 = new Customer(1001);
        Order o3 = new Order(createTime, 10, c3);
        o3.setWaitTime(4)
                .setPosition(4)
                .setOrderId(3);

        Customer c4 = new Customer(1002);
        Order o4 = new Order(createTime, 10, c4);
        o4.setWaitTime(5)
                .setPosition(5)
                .setOrderId(4);

        Customer c5 = new Customer(1003);
        Order o5 = new Order(createTime, 10, c5);
        o5.setWaitTime(3)
                .setPosition(3)
                .setOrderId(5);

        List<Order> orders = new ArrayList<>();
        orders.add(o1);
        orders.add(o2);
        orders.add(o5);
        orders.add(o3);
        orders.add(o4);
        return orders;
    }

}
