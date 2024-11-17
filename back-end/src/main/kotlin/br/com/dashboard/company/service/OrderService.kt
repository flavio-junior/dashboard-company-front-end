package br.com.dashboard.company.service

import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.OrderRepository
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.order.OrderRequestVO
import br.com.dashboard.company.vo.order.OrderResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class OrderService {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Transactional(readOnly = true)
    fun findAllOrders(): List<OrderResponseVO> {
        val orders = orderRepository.findAll()
        return orders.map { order -> parseObject(order, OrderResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findOrderById(
        id: Long
    ): OrderResponseVO {
        val order = getOrder(id = id)
        return parseObject(order, OrderResponseVO::class.java)
    }

    private fun getOrder(id: Long): Order {
        return orderRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(ORDER_NOT_FOUND) }
    }

    @Transactional
    fun createNewOrder(
        order: OrderRequestVO
    ): OrderResponseVO {
            val orderResult: Order = parseObject(order, Order::class.java)
            return parseObject(orderRepository.save(orderResult), OrderResponseVO::class.java)
    }

    fun deleteOrder(id: Long) {
        orderRepository.delete(getOrder(id = id))
    }

    companion object {
        const val ORDER_NOT_FOUND = "Order not found!"
    }
}