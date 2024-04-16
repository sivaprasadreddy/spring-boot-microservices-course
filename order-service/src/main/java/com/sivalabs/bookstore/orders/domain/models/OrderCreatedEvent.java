package com.sivalabs.bookstore.orders.domain.models;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderCreatedEvent(
        String eventId,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        LocalDateTime createdAt) {}
