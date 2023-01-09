package com.sample.ordermanagement.request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sample.ordermanagement.entity.Customer;
import com.sample.ordermanagement.service.OrderService;

@Component
public class CustomerPromotionJob {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 * * * *")
    public void checkCustomerPromotion() {
        List<Customer> customers = orderService.getCustomers();
        for (Customer customer : customers) {
            orderService.sendMail(customer.getId());
        }
    }
}