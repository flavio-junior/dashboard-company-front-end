package br.com.dashboard.company.service

import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.OrderRepository
import br.com.dashboard.company.service.ObjectService.Companion.OBJECT_NOT_FOUND
import br.com.dashboard.company.utils.common.Action
import br.com.dashboard.company.utils.common.Status
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.`object`.ObjectRequestVO
import br.com.dashboard.company.vo.`object`.UpdateObjectRequestVO
import br.com.dashboard.company.vo.order.CloseOrderRequestVO
import br.com.dashboard.company.vo.order.OrderRequestVO
import br.com.dashboard.company.vo.order.OrderResponseVO
import br.com.dashboard.company.vo.order.UpdateStatusDeliveryRequestVO
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
    private lateinit var addressService: AddressService

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
        val addressSaved = order.address?.let { addressService.saveAddress(addressRequestVO = it) }
        orderResult.address = addressSaved
        val objectsSaved = objectService.saveObjects(userId = user.id, objectsToSave = order.objects)
        orderResult.objects = objectsSaved.first
        orderResult.quantity = order.objects?.size ?: 0
        orderResult.total = objectsSaved.second
        orderResult.user = userAuthenticated
        return parseObject(orderRepository.save(orderResult), OrderResponseVO::class.java)
    }

    @Transactional
    fun incrementMoreObjectsOrder(
        user: User,
        orderId: Long,
        objects: MutableList<ObjectRequestVO>
    ) {
        val orderSaved = getOrder(orderId = orderId, userId = user.id)
        val objectsSaved = objectService.saveObjects(userId = user.id, order = orderSaved, objectsToSave = objects)
        orderRepository.updateQuantityOrder(orderId = orderId, objectsSaved.first?.size)
        incrementDataOrder(
            orderId = orderId,
            total = objectsSaved.second
        )
    }

    @Transactional
    fun updateObject(
        user: User,
        orderId: Long,
        objectId: Long,
        objectActual: UpdateObjectRequestVO
    ) {
        val orderSaved: Order = getOrder(userId = user.id, orderId = orderId)
        val identifiedObject = orderSaved.objects?.firstOrNull { it.id == objectId }
        if (identifiedObject != null) {
            val objectSaved = objectService.getObject(orderId = orderSaved.id, objectId = objectId)
            val priceCalculated = (objectSaved.price * objectActual.quantity)
            when (objectActual.action) {
                Action.UPDATE_STATUS -> {
                    objectService.updateStatusObject(
                        orderId = orderId,
                        objectId = objectId,
                        status = objectActual.status
                    )
                }

                Action.INCREMENT -> {
                    objectService.incrementMoreItemsObject(
                        orderId = orderId,
                        objectId = objectId,
                        quantity = objectActual.quantity,
                        total = priceCalculated
                    )
                    incrementDataOrder(
                        orderId = orderId,
                        total = priceCalculated
                    )
                }

                Action.DECREMENT -> {
                    objectService.decrementItemsObject(
                        orderId = orderId,
                        objectId = objectId,
                        quantity = objectActual.quantity,
                        total = priceCalculated
                    )
                    decrementDataOrder(
                        orderId = orderId,
                        total = priceCalculated
                    )
                }

                Action.REMOVE_OBJECT -> {
                    objectService.deleteObject(orderId = orderSaved.id, objectId = objectSaved.id)
                    decrementDataOrder(
                        orderId = orderId,
                        quantity = SUBTRACT_ONE,
                        total = objectSaved.total
                    )
                }
            }
        } else {
            throw ResourceNotFoundException(OBJECT_NOT_FOUND)
        }
    }

    @Transactional
    fun incrementDataOrder(
        orderId: Long,
        total: Double? = 0.0
    ) {
        orderRepository.incrementDataOrder(orderId = orderId, total = total)
    }

    @Transactional
    fun decrementDataOrder(
        orderId: Long,
        quantity: Int? = 0,
        total: Double? = 0.0
    ) {
        orderRepository.decrementDataOrder(orderId = orderId, quantity = quantity, total = total)
    }

    @Transactional
    fun closeOrder(
        user: User,
        idOrder: Long,
        closeOrder: CloseOrderRequestVO
    ) {
        val order = getOrder(userId = user.id, orderId = idOrder)
        updateStatusOrder(userId = user.id, orderId = idOrder, status = Status.CLOSED)
        paymentService.updatePayment(closeOrder = closeOrder, order = order)
        checkoutService.saveCheckoutDetails(total = order.total)
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
    fun updateStatusDelivery(
        user: User,
        orderId: Long,
        status: UpdateStatusDeliveryRequestVO
    ) {
        val orderSaved = getOrder(userId = user.id, orderId = orderId)
        addressService.updateStatusDelivery(
            orderId = orderSaved.id,
            addressId = orderSaved.address?.id,
            status = status
        )
    }

    @Transactional
    fun deleteOrder(
        user: User,
        orderId: Long
    ) {
        val orderSaved: Order = getOrder(userId = user.id, orderId = orderId)
        orderRepository.deleteOrderById(userId = user.id, orderId = orderSaved.id)
    }

    companion object {
        const val ORDER_NOT_FOUND = "Order not found!"
        const val SUBTRACT_ONE = 1
    }
}
