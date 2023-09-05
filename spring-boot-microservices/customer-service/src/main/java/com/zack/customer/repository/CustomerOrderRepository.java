package com.zack.customer.repository;

import com.zack.customer.entity.CustomerOrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrdersEntity, Long> {

    List<CustomerOrdersEntity> findByCustomerId(Long customerId);

    CustomerOrdersEntity findByCustomerIdAndOrderId(Long customerId, Long orderId);

    CustomerOrdersEntity findByOrderId(Long orderId);


}
