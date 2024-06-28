package com.masai.services;

import com.masai.exception.NotFoundException;
import com.masai.model.Customer;
import com.masai.model.OrderEntity;
import com.masai.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private List<OrderEntity> orders = new ArrayList<>();

    @Override
    public List<OrderEntity> getOrdersByCustomerId(Long customerId) {
        // Example logic to filter orders by customerId
        return orders.stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + customerId));
    }

}
