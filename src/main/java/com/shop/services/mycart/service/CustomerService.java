package com.shop.services.mycart.service;

import com.shop.services.mycart.dao.CustomerRepository;
import com.shop.services.mycart.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService
{

    @Autowired
    CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer custId)
    {
        customerRepository.deleteById(custId);
    }
}
