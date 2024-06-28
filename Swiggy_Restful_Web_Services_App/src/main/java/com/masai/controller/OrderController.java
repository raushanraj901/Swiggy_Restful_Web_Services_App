package com.masai.controller;

import com.masai.exception.NotFoundException;
import com.masai.model.OrderEntity;
import com.masai.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderEntity order) {
        orderService.placeOrder(order);
        return ResponseEntity.ok("Order placed successfully");
    }

    @PostMapping("/{orderId}/assignDelivery")
    public ResponseEntity<String> assignDelivery(@PathVariable Long orderId, @RequestParam Long deliveryPartnerId) {
        orderService.assignDelivery(orderId, deliveryPartnerId);
        return ResponseEntity.ok("Delivery assigned successfully");
    }

    @PostMapping("/{orderId}/updateStatus")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String newStatus) {
        orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok("Order status updated successfully");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long orderId) {
        OrderEntity order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found with id: " + orderId);
        }
        return ResponseEntity.ok(order);
    }
    
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderEntity>> getCustomerOrders(@PathVariable Long customerId) {
        List<OrderEntity> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public Page<OrderEntity> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "orderId") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return orderService.getAllOrders(pageable);
    }
}
