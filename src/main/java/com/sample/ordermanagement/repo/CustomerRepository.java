package com.sample.ordermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.ordermanagement.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	void deleteById(Long id);
}