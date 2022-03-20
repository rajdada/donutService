package com.shop.services.mycart.dao;

import com.shop.services.mycart.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{



}
