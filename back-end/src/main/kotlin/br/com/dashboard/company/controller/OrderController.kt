package br.com.dashboard.company.controller

import br.com.dashboard.company.service.OrderService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/dashboard/company/orders/v1"])
@Tag(name = "Order", description = "EndPoint For Managing All Orders")
class OrderController {

    @Autowired
    private lateinit var orderService: OrderService
}