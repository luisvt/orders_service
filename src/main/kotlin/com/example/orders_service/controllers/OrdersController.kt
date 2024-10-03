package com.example.orders_service.controllers

import com.example.orders_service.models.Order
import com.example.orders_service.repositories.OrdersRepository
import com.example.orders_service.services.OrdersService
import java.lang.RuntimeException
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/orders")
class OrdersController(
    private val ordersService: OrdersService,
    private val ordersRepository: OrdersRepository,
) {

    @GetMapping("/{id}")
    @ResponseBody
    fun findById(@PathVariable id: Long): Order {

        return ordersRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Not Found"
                )
            }
    }


    @GetMapping
    @ResponseBody
    fun findAll(
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<Order> {
        return ordersRepository.findAll(PageRequest.of(page, size))
    }


    @PostMapping
    @ResponseBody
    fun create(@RequestBody order: Order): Order {
        return ordersService.create(order)
    }
}