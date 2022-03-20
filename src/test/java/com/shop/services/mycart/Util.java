package com.shop.services.mycart;

import com.shop.services.mycart.model.Customer;
import com.shop.services.mycart.model.Order;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Util
{

    public static final long createTime = new Date().getTime();

    public static List<Order> prepareOrders()
    {

        Customer c1 = new Customer(1);
        Order o1 = new Order(createTime, 10, c1);
        o1.setOrderId(1);

        Customer c2 = new Customer(2);
        Order o2 = new Order(createTime, 10, c2);
        o2.setOrderId(2);

        Customer c3 = new Customer(1001);
        Order o3 = new Order(createTime, 10, c3);
        o3.setOrderId(3);

        Customer c4 = new Customer(1002);
        Order o4 = new Order(createTime, 10, c4);
        o4.setOrderId(4);

        Customer c5 = new Customer(1003);
        Order o5 = new Order(createTime, 10, c5);
        o5.setOrderId(5);

        List<Order> orders = new LinkedList<>();
        orders.add(o1);
        orders.add(o2);
        orders.add(o5);
        orders.add(o3);
        orders.add(o4);

        return orders;
    }

    public static List<Order> expectedOrders()
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

        List<Order> orders = new LinkedList<>();
        orders.add(o1);
        orders.add(o2);
        orders.add(o5);
        orders.add(o3);
        orders.add(o4);
        return orders;
    }

    public static List<Order> expectedNextDeliveries()
    {

        Customer c1 = new Customer(1);
        Order o1 = new Order(createTime, 20, c1);
        o1.setWaitTime(1)
                .setPosition(1)
                .setOrderId(1);

        Customer c2 = new Customer(2);
        Order o2 = new Order(createTime, 20, c2);
        o2.setWaitTime(2)
                .setPosition(2)
                .setOrderId(2);

        Customer c5 = new Customer(1003);
        Order o5 = new Order(createTime, 10, c5);
        o5.setWaitTime(3)
                .setPosition(3)
                .setOrderId(5);

        List<Order> orders = new LinkedList<>();
        orders.add(o1);
        orders.add(o2);
        orders.add(o5);
        return orders;
    }

    public static void setFinalStatic(Field field, Object newValue) throws Exception
    {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

}
