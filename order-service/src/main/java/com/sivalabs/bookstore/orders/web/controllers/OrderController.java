package com.sivalabs.bookstore.orders.web.controllers;

import com.sivalabs.bookstore.orders.domain.OrderNotFoundException;
import com.sivalabs.bookstore.orders.domain.OrderService;
import com.sivalabs.bookstore.orders.domain.SecurityService;
import com.sivalabs.bookstore.orders.domain.models.CreateOrderRequest;
import com.sivalabs.bookstore.orders.domain.models.CreateOrderResponse;
import com.sivalabs.bookstore.orders.domain.models.OrderDTO;
import com.sivalabs.bookstore.orders.domain.models.OrderSummary;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "security_auth")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoginUserName();
        log.info("Creating order for user: {}", userName);
        return orderService.createOrder(userName, request);
    }

    @GetMapping
    List<OrderSummary> getOrders() {
        String userName = securityService.getLoginUserName();
        log.info("Fetching orders for user: {}", userName);
        return orderService.findOrders(userName);
    }

    @GetMapping(value = "/{orderNumber}")
    OrderDTO getOrder(@PathVariable(value = "orderNumber") String orderNumber) {
        log.info("Fetching order by id: {}", orderNumber);
        String userName = securityService.getLoginUserName();
        return orderService
                .findUserOrder(userName, orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
