package com.example.orders_service.controllers

import net.javacrumbs.jsonunit.spring.jsonContent
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class OrdersControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {

    @Test
    @Order(1)
    fun `Get Order 1 Not Found`() {
        mockMvc.get("/api/orders/1") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
            content {
                string("")
            }
        }
    }

    @Test
    @Order(2)
    fun `Create Order 1`() {
        mockMvc.post("/api/orders") {
            contentType = MediaType.APPLICATION_JSON
            content = """{
  "orderLines": [
    {
      "product": {"id": 1},
      "quantity": 2
    }]
}"""
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content {
                jsonContent {
                    isEqualTo(
                        """{"orderLines":[
                            |{"product":{"name":"Apple","price":0.6000000000,
                            |"offer":"buyOneGetOneFree","id":1},"quantity":2}],
                            |"totalAmount":0.6000000000,
                            |"createdOn":"${'$'}{json-unit.ignore}",
                            |"id":1}""".trimMargin()
                    )
                }
            }
        }
    }

    @Test
    @Order(3)
    fun `Get Orders Including 1`() {
        mockMvc.get("/api/orders") {
            accept = MediaType.APPLICATION_JSON
            content = """{
  "orderLines": [
    {
      "product": {"id": 1},
      "quantity": 2
    }]
}"""
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content {
                jsonContent {
                    isEqualTo(
                        """{"content":[
                            |{"orderLines":[
                            |{"product":{"name":"Apple","price":0.6000000000,"offer":"buyOneGetOneFree","id":1},
                            |"quantity":2
                            |}],
                            |"totalAmount":0.60,
                            |"createdOn":"${'$'}{json-unit.ignore}",
                            |"id":1}],
                            |"pageable":{"pageNumber":0,"pageSize":20,
                            |"sort":{"sorted":false,"empty":true,"unsorted":true},
                            |"offset":0,"paged":true,"unpaged":false},
                            |"last":true,"totalPages":1,
                            |"totalElements":1,
                            |"size":20,"number":0,
                            |"sort":{"sorted":false,"empty":true,"unsorted":true},
                            |"first":true,
                            |"numberOfElements":1,
                            |"empty":false}""".trimMargin()
                    )
                }
            }
        }
    }

    @Test
    @Order(4)
    fun `Get Order 1 Found`() {
        mockMvc.get("/api/orders/1") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content {
                jsonContent {
                    isEqualTo(
                        """{"orderLines":[
                            |{"product":{"name":"Apple","price":0.6000000000,
                            |"offer":"buyOneGetOneFree","id":1},"quantity":2}],
                            |"totalAmount":0.60,
                            |"createdOn":"${'$'}{json-unit.ignore}",
                            |"id":1}""".trimMargin()
                    )
                }
            }
        }
    }

}