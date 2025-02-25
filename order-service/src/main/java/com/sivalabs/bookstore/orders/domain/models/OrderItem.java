package com.sivalabs.bookstore.orders.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItem(
        @NotBlank(message = "Code is required") String code,
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Price is required") BigDecimal price,
        @NotNull(message = "Quantity is required") @Min(value = 1, message = "Min quantity must be 1") Integer quantity) {}
