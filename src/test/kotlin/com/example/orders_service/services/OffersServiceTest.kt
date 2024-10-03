package com.example.orders_service.services

import com.example.orders_service.models.OrderLine
import com.example.orders_service.models.Product
import java.math.BigDecimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class OffersServiceTest {

    @InjectMocks
    lateinit var offersService: OffersService

    @BeforeEach
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @ParameterizedTest
    @CsvSource(*[
        "1, 1",
        "2, 1",
        "3, 2",
        "4, 2",
        "5, 3",
        "6, 3",
        "7, 4",
        "8, 4",
    ])
    fun `Should Buy One and Get One Free`(quantity: Int, expected: Int) {
        val orderLine = OrderLine(
            product = Product("Apple", BigDecimal(0.60 )),
            quantity = quantity,
        )
        val result = offersService.buyOneGetOneFree(orderLine)

        assertEquals(BigDecimal(expected) * BigDecimal(0.60), result)
    }

    @ParameterizedTest
    @CsvSource(*[
        "1, 1",
        "2, 2",
        "3, 2",
        "4, 3",
        "5, 4",
        "6, 4",
        "7, 5",
        "8, 6",
        "9, 6",
        "10, 7",
        "11, 8",
        "12, 8",
    ])
    fun `Should Buy Three and Get the Price of Two`(quantity: Int, expected: Int) {
        val orderLine = OrderLine(
            product = Product("Orange", BigDecimal(0.25 )),
            quantity = quantity,
        )
        val result = offersService.buy3ForThePriceOf2(orderLine)

        assertEquals(BigDecimal(expected) * BigDecimal(0.25), result)
    }
}