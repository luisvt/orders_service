package com.example.orders_service.services

import com.example.orders_service.models.Order
import com.example.orders_service.models.OrderLine
import com.example.orders_service.models.Product
import java.math.BigDecimal
import java.math.RoundingMode
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class OrdersServiceTest(
    @Autowired private val ordersService: OrdersService
) {

    @Test
    fun shouldCreateOrder() {
        val order = Order(
            mutableSetOf(
                OrderLine(Product(null, null, 1), 2)
            )
        )
        val result = ordersService.create(order)

        assertNotNull(result.id)
        val orderLine0 = result.orderLines.elementAt(0)
        assertNotNull(orderLine0.id?.orderId)
        assertEquals(BigDecimal(1.20).setScale(10, RoundingMode.UP), result.totalAmount)
        assertEquals(1, orderLine0.id?.productId)
        assertEquals("Apple", orderLine0.product.name)
        assertEquals(BigDecimal(0.60).setScale(10, RoundingMode.UP), orderLine0.product.price)

    }

}