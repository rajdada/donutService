package com.shop.services.mycart.service;

import com.shop.services.mycart.constant.Constants;
import com.shop.services.mycart.dao.CustomerRepository;
import com.shop.services.mycart.dao.OrderRepository;
import com.shop.services.mycart.exception.MyCartException;
import com.shop.services.mycart.model.Customer;
import com.shop.services.mycart.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService
{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerService customerService;

    private static final LinkedList<Order> orderQueue = new LinkedList<>();

    public Order addOrder(final Order order)
    {
        final Optional<Order> alreadyPresent = orderRepository.getOrderByCustomerId(order.getCustomer().getCustId());

        if (alreadyPresent.isPresent())
            throw new MyCartException(Constants.EC_ORDER_IN_PROCESS, Constants.EM_ORDER_IN_PROCESS);

        final Customer customer = customerService.addCustomer(order.getCustomer());

        if (customer == null)
            throw new MyCartException(Constants.EC_UNABLE_TO_CREATE_CUSTOMER, Constants.EM_UNABLE_TO_CREATE_CUSTOMER);

        order.setCustomer(customer);

        // need to use entity manager. It will throw an error while merge
        return orderRepository.save(order);
    }

    public Order findByOrderId(final Integer orderId)
    {
        final Optional<Order> orderOp = orderRepository.findById(orderId);

        if (orderOp.isEmpty())
            throw new MyCartException(Constants.EC_ORDER_NOT_EXISTS, Constants.EM_ORDER_NOT_EXISTS);

        return orderOp.get();
    }

    public List<Order> fetchOrdersBySubmission()
    {
        final List<Order> orders = orderRepository.fetchOrdersBySubmission();

        if (orders.isEmpty())
            throw new MyCartException(Constants.EC_ORDER_NOT_EXISTS, Constants.EM_ORDER_NOT_EXISTS);

        final AtomicInteger waitTime = new AtomicInteger();
        final AtomicInteger position = new AtomicInteger();

        orders.forEach(o ->{
                    o.setWaitTime(waitTime.incrementAndGet());
                    o.setPosition(position.incrementAndGet());
                }
        );

        orderQueue.clear();
        orderQueue.addAll(orders);

        return orderQueue;
    }


    public Order getQueuePositionAndWaitTime(final Integer customerId)
    {
        return orderQueue.stream()
                .filter(o -> o.getCustomer().getCustId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new MyCartException(Constants.EC_ORDER_NOT_EXISTS, Constants.EM_ORDER_NOT_EXISTS));
    }

    public List<Order> getAllOrdersAndWaitTime()
    {
        return orderQueue;
    }

    public List<Order> nextDelivery()
    {
        if (orderQueue.isEmpty())
            throw new MyCartException(Constants.EC_ORDER_NOT_EXISTS, Constants.EM_ORDER_NOT_EXISTS);

        final List<Order> orders = new ArrayList<>();

        synchronized (this)
        {
            int quantity = 0;

            while(quantity < 50)
            {
                final Order order = orderQueue.peek();
                quantity += order.getQuantity();
                if (quantity <= 50)
                {
                    orders.add(orderQueue.poll());
                }
                else break;
            }
        }
        return orders;
    }

    public int deleteOrderByCustomerId(final Integer customerId)
    {
        int res = orderRepository.deleteOrder(customerId);

        if (res == 0)
            throw new MyCartException(Constants.EC_ORDER_NOT_EXISTS, Constants.EM_ORDER_NOT_EXISTS);

        customerService.deleteCustomer(customerId);

        return res;
    }

    public synchronized void clearOrderAfterProcess(final Order order)
    {
        orderQueue.remove(order);

        orderQueue.forEach(o ->
                    o.setWaitTime(o.getWaitTime() - 1)
                            .setPosition(o.getPosition() - 1)
                );
    }
}
