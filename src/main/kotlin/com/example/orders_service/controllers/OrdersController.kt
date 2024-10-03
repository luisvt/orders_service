package com.example.orders_service.controllers

import com.example.orders_service.models.Order
import com.example.orders_service.services.OrdersService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrdersController(
    private val ordersService: OrdersService,
) {

    @PostMapping
    @ResponseBody
    fun create(@RequestBody order: Order): Order {
        return ordersService.create(order)
    }
}