package com.example.orders_service.services

import com.example.orders_service.models.Order
import com.example.orders_service.models.OrderLineId
import com.example.orders_service.repositories.OrdersRepository
import com.example.orders_service.repositories.ProductsRepository
import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrdersService(
    private val ordersRepository: OrdersRepository,
    private val productsRepository: ProductsRepository,
) {
    private val logger = LoggerFactory.getLogger(OrdersService::class.java)

    @Transactional
    fun create(order: Order): Order {
        logger.info("Creating a new order")

        val newOrder = order.copy(
            totalAmount = BigDecimal.ZERO,
            orderLines = mutableSetOf(),
            createdOn = Date()
        )
        val savedOrder = ordersRepository.save(newOrder)
        order.orderLines.forEach {
            it.product = productsRepository.findById(it.product.id!!).orElseThrow()
            it.id = OrderLineId(savedOrder.id!!, it.product.id)
            it.order = savedOrder
            savedOrder.orderLines.add(it)
        }

        savedOrder.totalAmount  = order.orderLines.sumOf { orderDetail ->
            BigDecimal(orderDetail.quantity) * (orderDetail.product.price ?: BigDecimal(0))
        }
        return ordersRepository.save(savedOrder);
    }
}