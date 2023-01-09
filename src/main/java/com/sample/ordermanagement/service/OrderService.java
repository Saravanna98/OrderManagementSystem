package com.sample.ordermanagement.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.ordermanagement.entity.Customer;
import com.sample.ordermanagement.entity.CustomerType;
import com.sample.ordermanagement.entity.Order;
import com.sample.ordermanagement.repo.CustomerRepository;
import com.sample.ordermanagement.repo.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Long customerId, BigDecimal amount) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Order order = new Order();
        order.setCustomer(customer);
        order.setAmount(amount);
        if (customer.getType() == CustomerType.GOLD) {
            order.setDiscount(amount.multiply(BigDecimal.valueOf(0.1)));
        } else if (customer.getType() == CustomerType.PLATINUM) {
            order.setDiscount(amount.multiply(BigDecimal.valueOf(0.2)));
        }
        return orderRepository.save(order);
    }

    public void promoteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        List<Order> orders = orderRepository.findByCustomer(customer);
        if (orders.size() >= 10 && customer.getType() == CustomerType.REGULAR) {
            customer.setType(CustomerType.GOLD);
        } else if (orders.size() >= 20 && customer.getType() == CustomerType.GOLD) {
            customer.setType(CustomerType.PLATINUM);
        }
        customerRepository.save(customer);
    }

    public void sendMail(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        List<Order> orders = orderRepository.findByCustomer(customer);
        if (customer.getType() == CustomerType.REGULAR && orders.size() == 9) {
            System.out.println("Sent mail to customer: " + customer.getName() + " about approaching gold status");
        } else if (customer.getType() == CustomerType.GOLD && orders.size() == 19) {
            System.out.println("Sent mail to customer: " + customer.getName() + " about approaching platinum status");
        }
    }

	public List<Customer> getCustomers() {
		// TODO Auto-generated method stub
		return null;
	}
}
