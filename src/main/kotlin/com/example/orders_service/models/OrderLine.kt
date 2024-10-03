package com.example.orders_service.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "order_lines")
data class OrderLine(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    var product: Product,

    @Column(nullable = false)
    var quantity: Int,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    var order: Order? = null,

    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(name = "orderId", column = Column(name = "order_id", nullable = false)),
        AttributeOverride(name = "productId", column = Column(name = "product_id", nullable = false))
    )
    @JsonIgnore
    var id: OrderLineId? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderLine

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "OrderLine(id=$id, order=${order?.id}, product=$product, quantity=$quantity)"
    }
}
