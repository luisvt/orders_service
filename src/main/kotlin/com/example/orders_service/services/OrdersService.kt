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
import kotlin.reflect.full.memberFunctions

@Service
class OrdersService(
    private val ordersRepository: OrdersRepository,
    private val productsRepository: ProductsRepository,
    private val offersService: OffersService,
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

        savedOrder.totalAmount = order.orderLines.sumOf { orderLine ->
            if (orderLine.product.offer != null) {
                // next equivalent to: offerServices[orderLine.product.offer](orderLine)
                val method = OffersService::class.memberFunctions.find { it.name == orderLine.product.offer }
                method?.call(offersService, orderLine) as BigDecimal
            } else offersService.noDiscountPrice(orderLine)
        }
        return ordersRepository.save(savedOrder);
    }
}