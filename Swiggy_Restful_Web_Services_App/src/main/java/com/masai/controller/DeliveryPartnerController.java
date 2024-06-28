package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.model.DeliveryPartner;
import com.masai.services.DeliveryPartnerService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/delivery-partners")
public class DeliveryPartnerController {
    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @PostMapping("/add")
    public ResponseEntity<String> addDeliveryPartner(@RequestBody DeliveryPartner deliveryPartner) {
        deliveryPartnerService.addDeliveryPartner(deliveryPartner);
        return ResponseEntity.ok("Delivery partner added successfully");
    }

    @GetMapping("/{deliveryPartnerId}")
    public ResponseEntity<DeliveryPartner> getDeliveryPartnerById(@PathVariable Long deliveryPartnerId) {
        DeliveryPartner deliveryPartner = deliveryPartnerService.getDeliveryPartnerById(deliveryPartnerId);
        return ResponseEntity.ok(deliveryPartner);
    }

}
