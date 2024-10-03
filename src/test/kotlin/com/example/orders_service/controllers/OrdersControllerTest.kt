package com.example.orders_service.controllers

import net.javacrumbs.jsonunit.spring.jsonContent
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class OrdersControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {

    @Test
    fun `Create Order`() {
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
                        """{"orderLines":[{"product":{"name":"Apple","price":0.6000000000,"id":1},"quantity":2}],
                            |"totalAmount":1.2000000000,"createdOn":"${'$'}{json-unit.ignore}","id":1}""".trimMargin()
                    )
                }
            }
        }
    }

}