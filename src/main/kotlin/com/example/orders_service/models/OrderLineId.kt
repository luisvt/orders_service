package com.example.orders_service.models

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class OrderLineId(
    @Column(nullable = false)
    var orderId: Long?,

    @Column(nullable = false)
    var productId: Long?,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderLineId

        if (orderId != other.orderId) return false
        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = orderId ?: 0
        result = 31 * result + (productId ?: 0)
        return result.toInt()
    }
}
