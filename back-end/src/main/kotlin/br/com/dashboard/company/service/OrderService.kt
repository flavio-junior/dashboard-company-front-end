package br.com.dashboard.company.service

import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.OrderRepository
import br.com.dashboard.company.utils.common.AddressStatus
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
        val orders: Page<Order>? =
            orderRepository.findAllOrdersOpen(userId = user.id, status = status, pageable = pageable)
        return orders?.map { order -> parseObject(order, OrderResponseVO::class.java) }
            ?: throw ResourceNotFoundException(message = ORDER_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun findOrderById(
        user: User,
        orderId: Long
    ): OrderResponseVO {
        val order = getOrder(userId = user.id, orderId = orderId)
        return parseObject(order, OrderResponseVO::class.java)
    }

    private fun getOrder(
        userId: Long,
        orderId: Long
    ): Order {
        val orderSaved: Order? = orderRepository.findOrderById(userId = userId, orderId = orderId)
        if (orderSaved != null) {
            return orderSaved
        } else {
            throw ResourceNotFoundException(ORDER_NOT_FOUND)
        }
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
        orderResult.address?.status = AddressStatus.PENDING_DELIVERY
        orderResult.objects = objectsSaved.first
        orderResult.quantity = order.objects?.size ?: 0
        orderResult.price = objectsSaved.second
        orderResult.user = userAuthenticated
        return parseObject(orderRepository.save(orderResult), OrderResponseVO::class.java)
    }

    @Transactional
    fun updateObject(
        user: User,
        orderId: Long,
        objectId: Long,
        objectActual: UpdateObjectRequestVO
    ) {
        objectService.updateObject(user = user, orderId = orderId, objectId = objectId, objectActual = objectActual)
    }

    @Transactional
    fun closeOrder(
        user: User,
        idOrder: Long,
        closeOrder: CloseOrderRequestVO
    ) {
        val order = getOrder(userId = user.id, orderId = idOrder)
        updateStatusOrder(userId = user.id, orderId = idOrder, status = Status.CLOSED)
        paymentService.updatePayment(closeOrder = closeOrder)
        checkoutService.saveCheckoutDetails(total = order.price)
    }

    @Transactional
    fun updateStatusOrder(
        userId: Long,
        orderId: Long,
        status: Status
    ) {
        orderRepository.updateStatusOrder(userId = userId, orderId = orderId, status = status)
    }

    @Transactional
    fun deleteOrder(
        user: User,
        orderId: Long
    ) {
        val orderSaved:Order = getOrder(userId = user.id, orderId = orderId)
        orderRepository.deleteOrderById(userId = user.id, orderId = orderSaved.id)
    }

    companion object {
        const val ORDER_NOT_FOUND = "Order not found!"
    }
}
