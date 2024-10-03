package com.example.orders_service.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(
    @Column(name = "name", nullable = false, unique = true, length = 50)
    var name: String?,

    @Column(name = "price", nullable = false, scale = 10, precision = 10)
    var price: BigDecimal?,

    @Column(name = "offer", length = 50)
    var offer: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)