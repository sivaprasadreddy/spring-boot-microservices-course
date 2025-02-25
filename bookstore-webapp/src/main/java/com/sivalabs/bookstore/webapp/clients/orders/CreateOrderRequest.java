package com.sivalabs.bookstore.webapp.clients.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

public record CreateOrderRequest(
        @NotBlank(message = "Items cannot be empty.") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid Address deliveryAddress)
        implements Serializable {}
