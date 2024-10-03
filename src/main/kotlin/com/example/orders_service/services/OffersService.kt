package com.example.orders_service.services

import com.example.orders_service.models.OrderLine
import java.math.BigDecimal
import org.springframework.stereotype.Service

@Service
class OffersService {

    fun buyOneGetOneFree(orderLine: OrderLine): BigDecimal {
        val discountedQuantity = orderLine.quantity - orderLine.quantity / 2
        return BigDecimal(discountedQuantity) * orderLine.product.price!!
    }

    fun buy3ForThePriceOf2(orderLine: OrderLine): BigDecimal {
        val discountedQuantity = orderLine.quantity - orderLine.quantity / 3
        return BigDecimal(discountedQuantity) * orderLine.product.price!!
    }

    fun noDiscountPrice(orderLine: OrderLine): BigDecimal {
        return BigDecimal(orderLine.quantity) * orderLine.product.price!!
    }
}