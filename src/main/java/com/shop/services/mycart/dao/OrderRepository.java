package com.shop.services.mycart.dao;

import com.shop.services.mycart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer>
{

    @Query("SELECT o FROM Order o WHERE o.customer.custId = ?1")
    Optional<Order> getOrderByCustomerId(@NonNull Integer customerId);


    @Query("select o from Order o join o.customer c order by o.createTime, case when c.isPremium = 'false' then 0 else 1 end desc")
    List<Order> fetchOrdersBySubmission();

    @Modifying
    @Query("delete from Order o WHERE o.customer.custId = ?1")
    int deleteOrder(@NonNull Integer customerId);


}
