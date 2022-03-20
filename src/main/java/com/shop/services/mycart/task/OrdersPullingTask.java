package com.shop.services.mycart.task;


import com.shop.services.mycart.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrdersPullingTask
{
    private static final Logger LOG = LoggerFactory.getLogger(OrdersPullingTask.class);

    @Autowired
    OrderService orderService;
    
    @Scheduled(cron = "${pulling.cron.expression}")
    public void scheduleTask()
    {
        orderService.fetchOrdersBySubmission();
        LOG.info("Orders queue updated...");
    }
}
