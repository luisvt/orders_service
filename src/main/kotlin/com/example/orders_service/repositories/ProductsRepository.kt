package com.example.orders_service.repositories

import com.example.orders_service.models.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductsRepository : JpaRepository<Product, Long> {
}