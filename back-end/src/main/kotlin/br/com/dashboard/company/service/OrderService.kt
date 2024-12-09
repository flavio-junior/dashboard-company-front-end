package br.com.dashboard.company.service

import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.OrderRepository
import br.com.dashboard.company.utils.common.Status
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.`object`.UpdateObjectRequestVO
import br.com.dashboard.company.vo.order.CloseOrderRequestVO
import br.com.dashboard.company.vo.order.OrderRequestVO
import br.com.dashboard.company.vo.order.OrderResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Repository
class OrderService {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var objectService: ObjectService

    @Autowired
    private lateinit var paymentService: PaymentService

    @Autowired
    private lateinit var checkoutService: CheckoutService

    @Autowired
    private lateinit var userService: UserService

    @Transactional(readOnly = true)
    fun findAllOrders(
        user: User,
        status: Status,
        pageable: Pageable
    ): Page<OrderResponseVO> {
        val orders: Page<Order>? = orderRepository.findAllOrdersOpen(userId = user.id, status = status, pageable = pageable)
        return orders?.map { order -> parseObject(order, OrderResponseVO::class.java) }
            ?: throw ResourceNotFoundException(message = ORDER_NOT_FOUND)
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
        user: User,
        order: OrderRequestVO
    ): OrderResponseVO {
        val userAuthenticated = userService.findUserById(id = user.id)
        val orderResult: Order = parseObject(order, Order::class.java)
        orderResult.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        orderResult.status = Status.OPEN
        val objectsSaved = objectService.saveObjects(userId = user.id, objectsToSave = order.objects)
        orderResult.objects = objectsSaved.first
        orderResult.quantity = order.objects?.size ?: 0
        orderResult.price = objectsSaved.second
        orderResult.user = userAuthenticated
        return parseObject(orderRepository.save(orderResult), OrderResponseVO::class.java)
    }

    @Transactional
    fun updateObject(
        idOrder: Long,
        idObject: Long,
        objectActual: UpdateObjectRequestVO
    ) {
        objectService.updateObject(idOrder = idOrder, idObject = idObject, objectActual = objectActual)
    }

    @Transactional
    fun closeOrder(
        user: User,
        idOrder: Long,
        closeOrder: CloseOrderRequestVO
    ) {
        val order = getOrder(id = idOrder)
        updateStatusOrder(userId = user.id, idOrder = idOrder, status = Status.CLOSED)
        paymentService.updatePayment(closeOrder = closeOrder)
        checkoutService.saveCheckoutDetails(total = order.price)
    }

    @Transactional
    fun updateStatusOrder(
        userId: Long,
        idOrder: Long,
        status: Status
    ) {
        orderRepository.updateStatusOrder(userId = userId, orderId = idOrder, status = status)
    }

    fun deleteOrder(id: Long) {
        orderRepository.delete(getOrder(id = id))
    }

    companion object {
        const val ORDER_NOT_FOUND = "Order not found!"
    }
}
