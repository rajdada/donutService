package com.shop.services.mycart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name = "Customer")
@Table(name = "Customer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cust_id") })
public class Customer
{
    private static final int PREMIUM_CUSTOMER_BORDER = 1000;

    @Id
    @Column(name = "cust_id", unique = true, nullable = false)
    private Integer custId;

    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium;

    @OneToOne(mappedBy = "customer")
    @JsonBackReference
    private Order order;

    public Customer()
    {
    }

    public Customer(Integer custId)
    {
        this.custId = custId;
        this.isPremium = custId < PREMIUM_CUSTOMER_BORDER;
    }

    public Integer getCustId()
    {
        return custId;
    }

    public void setCustId(Integer custId)
    {
        this.custId = custId;
    }

    public Boolean getPremium()
    {
        return isPremium;
    }

    public void setPremium(Boolean premium)
    {
        isPremium = premium;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}
