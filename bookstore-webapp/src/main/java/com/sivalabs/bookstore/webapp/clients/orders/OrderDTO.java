package com.sivalabs.bookstore.webapp.clients.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Set;

public record OrderDTO(
        Long id,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        OrderStatus status,
        String comments) {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
