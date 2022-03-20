package com.shop.services.mycart.task;


import com.shop.services.mycart.model.Order;
import com.shop.services.mycart.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrdersProcessingTask
{
    private static final Logger LOG = LoggerFactory.getLogger(OrdersProcessingTask.class);

    @Autowired
    OrderService orderService;
    
    @Scheduled(cron = "${processing.cron.expression}")
    public void scheduleTask()
    {
        final List<Order> orders = orderService.nextDelivery();

        if (orders == null || orders.isEmpty())
            LOG.warn("Error in retrieving  orders ");

        else
        {
            orders.forEach(order -> {
                LOG.info("Processing order : "+ order.toString());

                orderService.deleteOrderByCustomerId(order.getCustomer().getCustId());
                orderService.clearOrderAfterProcess(order);
            });
        }
    }
}
