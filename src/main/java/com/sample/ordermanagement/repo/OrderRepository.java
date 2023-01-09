package com.sample.ordermanagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.ordermanagement.entity.Customer;
import com.sample.ordermanagement.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByCustomer(Customer customer);
}
