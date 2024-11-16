package br.com.dashboard.company.service

import br.com.dashboard.company.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class OrderService {

    @Autowired
    private lateinit var orderRepository: OrderRepository
}