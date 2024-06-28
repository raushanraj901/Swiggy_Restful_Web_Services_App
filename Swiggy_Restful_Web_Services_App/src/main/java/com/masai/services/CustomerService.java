package com.masai.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.masai.model.Customer;
import com.masai.model.OrderEntity;



@Service
public interface CustomerService {
	List<OrderEntity> getOrdersByCustomerId(Long customerId);
	void addCustomer(Customer customer);

    Customer getCustomerById(Long customerId);
}



