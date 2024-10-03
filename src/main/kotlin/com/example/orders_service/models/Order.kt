package com.example.orders_service.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import jakarta.validation.constraints.NotEmpty
import java.math.BigDecimal
import java.util.*
import org.springframework.data.annotation.CreatedDate

@Entity
@Table(name = "orders")
data class Order(
    @NotEmpty
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "order")
    var orderLines: MutableSet<OrderLine>,

    @Column(nullable = false, scale = 10)
    var totalAmount: BigDecimal = BigDecimal.ZERO,

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    var createdOn: Date = Date(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)