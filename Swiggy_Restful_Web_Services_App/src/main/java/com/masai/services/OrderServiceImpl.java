package com.masai.services;

import com.masai.exception.InvalidStatusTransitionException;
import com.masai.exception.NotFoundException;
import com.masai.model.Customer;
import com.masai.model.OrderEntity;
import com.masai.model.OrderEntity.OrderStatus;
import com.masai.model.Restaurant;
import com.masai.repositories.CustomerRepository;
import com.masai.repositories.OrderRepository;
import com.masai.repositories.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public void placeOrder(OrderEntity order) {
        logger.info("Placing order: {}", order);

        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + order.getCustomerId()));

        Restaurant restaurant = restaurantRepository.findById(order.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("Restaurant not found with ID: " + order.getRestaurantId()));

        order.setOrderStatus(OrderEntity.OrderStatus.PLACED);

        orderRepository.save(order);

        logger.info("Order placed successfully with ID: {}", order.getOrderId());
    }   

    @Override
    @Transactional
    public void assignDelivery(Long orderId, Long deliveryPartnerId) {
        OrderEntity order = getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with ID: " + orderId);
        }

        order.setDeliveryPartnerId(deliveryPartnerId);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String newStatus) {
        OrderEntity order = getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with ID: " + orderId);
        }

        validateStatusTransition(order.getOrderStatus(), newStatus);

        order.setOrderStatus(OrderStatus.valueOf(newStatus)); // Assuming OrderStatus is an enum
        orderRepository.save(order);
    }

    
    public List<OrderEntity> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    private void validateStatusTransition(OrderStatus oldStatus, String newStatus) {
        if (oldStatus.equals(OrderStatus.DELIVERED) && !newStatus.equals(OrderStatus.DELIVERED.name())) {
            throw new InvalidStatusTransitionException("Invalid status transition from " + oldStatus + " to " + newStatus);
        }
    }

	@Override
	public Page<OrderEntity> getAllOrders(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}
}
