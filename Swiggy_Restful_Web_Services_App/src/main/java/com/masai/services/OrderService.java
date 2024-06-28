package com.masai.services;

import com.masai.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface OrderService {

    void placeOrder(OrderEntity order);

    void assignDelivery(Long orderId, Long deliveryPartnerId);

    void updateOrderStatus(Long orderId, String newStatus);

    List<OrderEntity> getOrdersByCustomerId(Long customerId);

    OrderEntity getOrderById(Long orderId);

    Page<OrderEntity> getAllOrders(Pageable pageable);
}
