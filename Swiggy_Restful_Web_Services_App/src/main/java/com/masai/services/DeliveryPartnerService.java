package com.masai.services;

import org.springframework.stereotype.Service;

import com.masai.exception.NotFoundException;
import com.masai.model.DeliveryPartner;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryPartnerService {
    private List<DeliveryPartner> deliveryPartners = new ArrayList<>();

    public void addDeliveryPartner(DeliveryPartner deliveryPartner) {
        deliveryPartners.add(deliveryPartner);
    }

    public DeliveryPartner getDeliveryPartnerById(Long deliveryPartnerId) {
        return deliveryPartners.stream()
                .filter(dp -> dp.getDeliveryPartnerId().equals(deliveryPartnerId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Delivery Partner not found with id: " + deliveryPartnerId));
    }
}

