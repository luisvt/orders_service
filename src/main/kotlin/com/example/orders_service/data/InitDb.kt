package com.example.orders_service.data

import com.example.orders_service.models.Product
import com.example.orders_service.repositories.OrdersRepository
import com.example.orders_service.repositories.ProductsRepository
import java.math.BigDecimal
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("default")
class InitDb(
    val ordersRepository: OrdersRepository,
    val productsRepository: ProductsRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val totalProducts = productsRepository.count()
        if (totalProducts == 0L) {
            val prod1 = Product("Apple", BigDecimal(0.60))
            val prod2 = Product("Orange", BigDecimal(0.25))

            productsRepository.saveAllAndFlush(listOf(prod1, prod2))
        }
    }
}