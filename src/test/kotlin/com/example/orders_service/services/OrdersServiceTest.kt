package com.example.orders_service.services

import com.example.orders_service.models.Order
import com.example.orders_service.models.OrderLine
import com.example.orders_service.models.Product
import com.example.orders_service.repositories.ProductsRepository
import java.math.BigDecimal
import java.math.RoundingMode
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class OrdersServiceTest(
    @Autowired private val ordersService: OrdersService
) {

    @Autowired
    private lateinit var productsRepository: ProductsRepository

    @ParameterizedTest
    @CsvSource(*[
        "1, 1, 1",
        "1, 2, 1",
        "1, 3, 2",
        "1, 4, 2",
        "1, 5, 3",
        "1, 6, 3",
        "1, 7, 4",
        "1, 8, 4",
        "2, 1, 1",
        "2, 2, 2",
        "2, 3, 2",
        "2, 4, 3",
        "2, 5, 4",
        "2, 6, 4",
        "2, 7, 5",
        "2, 8, 6",
        "2, 9, 6",
        "2, 10, 7",
        "2, 11, 8",
        "2, 12, 8",
    ])
    fun shouldCreateOrder(productId: Long, quantity: Int, expected: Int) {
        val product = productsRepository.findById(productId).orElseThrow()

        val order = Order(
            mutableSetOf(
                OrderLine(Product(null, null, null, productId), quantity)
            )
        )
        val result = ordersService.create(order)

        assertNotNull(result.id)
        val orderLine0 = result.orderLines.elementAt(0)
        assertNotNull(orderLine0.id?.orderId)
        assertEquals(product.price!! * BigDecimal(expected),
            result.totalAmount)
        assertEquals(productId, orderLine0.id?.productId)
        assertEquals(product.name, orderLine0.product.name)
        assertEquals(product.price!!, orderLine0.product.price)

    }

}