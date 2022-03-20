package com.shop.services.mycart.service;


import com.shop.services.mycart.Util;
import com.shop.services.mycart.dao.OrderRepository;
import com.shop.services.mycart.model.Customer;
import com.shop.services.mycart.model.Order;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceTest
{

    @SpyBean
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @Test
    @org.junit.jupiter.api.Order(1)
    public void addOrder()
    {
        //given
        Customer customer = new Customer(1);

        Order order = new Order(Util.createTime, 10, customer);
        order.setOrderId(1);

        when(orderRepository.save(any())).thenReturn(order);

        //when
        Order savedOrder = orderService.addOrder(order);

        //then
        verify(orderRepository).save(any());
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder).isEqualTo(order);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void findByOrderId()
    {
        //given
        Customer customer = new Customer(1);

        Order order = new Order(Util.createTime, 10, customer);
        order.setOrderId(1);

        when(orderRepository.findById(anyInt())).thenReturn(java.util.Optional.of(order));

        //when
        Order fetchedOrder = orderService.findByOrderId(1);

        //then
        verify(orderRepository).findById(anyInt());
        assertThat(fetchedOrder).isNotNull();
        assertThat(fetchedOrder).isEqualTo(order);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void deleteOrder()
    {
        //given
        when(orderRepository.deleteOrder(anyInt())).thenReturn(1);

        //when
        int res = orderService.deleteOrderByCustomerId(1);

        //then
        verify(orderRepository).deleteOrder(anyInt());
        assertThat(res).isEqualTo(1);
    }

    @Test
    public void fetchOrdersBySubmission() throws Exception
    {
        //given
        List<Order> orders = Util.prepareOrders();
        when(orderRepository.fetchOrdersBySubmission()).thenReturn(orders);

        //when
        List<Order> fetchedOrders = orderService.fetchOrdersBySubmission();

        assertThat(fetchedOrders).isNotNull();
        assertThat(fetchedOrders).isEqualTo(Util.expectedOrders());
    }

    @Test
    public void getQueuePositionAndWaitTime() throws Exception
    {
        //given
        List<Order> orders = Util.expectedOrders();
        Util.setFinalStatic(OrderService.class.getDeclaredField("orderQueue"), orders);

        //when
        Order order = orderService.getQueuePositionAndWaitTime(1001);

        //then
        assertThat(order).isNotNull();
        assertThat(order.getPosition()).isEqualTo(4);
        assertThat(order.getWaitTime()).isEqualTo(4);
    }

    @Test
    public void getAllOrdersAndWaitTime() throws Exception
    {
        //given
        List<Order> orders = Util.expectedOrders();
        Util.setFinalStatic(OrderService.class.getDeclaredField("orderQueue"), orders);

        //when
        orders = orderService.getAllOrdersAndWaitTime();

        //then
        assertThat(orders).isNotNull();
    }

    @Test
    public void nextDelivery() throws Exception
    {
        //given
        List<Order> orders = Util.expectedOrders();
        Util.setFinalStatic(OrderService.class.getDeclaredField("orderQueue"), orders);

        //when
        List<Order> resOrders = orderService.nextDelivery();

        //then
        assertThat(resOrders).isNotNull();
        assertThat(resOrders.get(0).getPosition()).isEqualTo(1);
    }

    @Test
    public void clearOrderAfterProcess() throws Exception
    {
        //given
        List<Order> orders = Util.expectedOrders();
        Util.setFinalStatic(OrderService.class.getDeclaredField("orderQueue"), orders);

        Customer customer = new Customer(1);

        Order order = new Order(Util.createTime, 10, customer);
        order.setOrderId(1);


        //when
        orderService.clearOrderAfterProcess(order);

        //then
        assertThat(orders).isNotNull();
        assertThat(orders.size()).isEqualTo(4);
    }





}
