package com.example.orders_service.repositories

import com.example.orders_service.models.Order
import org.springframework.data.jpa.repository.JpaRepository


interface OrdersRepository : JpaRepository<Order, Long> {
}