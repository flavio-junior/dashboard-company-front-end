package br.com.dashboard.company.service

import br.com.dashboard.company.entities.`object`.Object
import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.ObjectRepository
import br.com.dashboard.company.utils.common.ObjectStatus
import br.com.dashboard.company.utils.common.TypeItem
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.`object`.ObjectRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ObjectService {

    @Autowired
    private lateinit var objectRepository: ObjectRepository

    @Autowired
    private lateinit var itemService: ItemService

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var foodService: FoodService

    @Autowired
    private lateinit var userService: UserService

    @Transactional
    fun saveObjects(
        userId: Long,
        order: Order? = null,
        objectsToSave: MutableList<ObjectRequestVO>? = null
    ): Pair<MutableList<Object>?, Double> {
        var total = 0.0
        val userAuthenticated = userService.findUserById(id = userId)
        val result = objectsToSave?.map { item ->
            when (item.type) {
                TypeItem.FOOD -> {
                    val objectSaved = foodService.getFood(userId = userId, foodId = item.identifier)
                    val objectResult: Object = parseObject(objectsToSave, Object::class.java)
                    objectResult.identifier = item.identifier
                    objectResult.type = item.type
                    objectResult.name = objectSaved.name
                    objectResult.price = objectSaved.price
                    objectResult.quantity = item.quantity
                    val priceCalculated = (objectSaved.price * item.quantity)
                    objectResult.total = priceCalculated
                    objectResult.status = ObjectStatus.PENDING
                    objectResult.order  = order
                    objectSaved.user = userAuthenticated
                    total += priceCalculated
                    objectRepository.save(objectResult)
                }

                TypeItem.ITEM -> {
                    val objectSaved = itemService.getItem(userId = userId, itemId = item.identifier)
                    val objectResult: Object = parseObject(objectsToSave, Object::class.java)
                    objectResult.identifier = item.identifier
                    objectResult.type = item.type
                    objectResult.name = objectSaved.name
                    objectResult.price = objectSaved.price
                    objectResult.quantity = item.quantity
                    val priceCalculated = (objectSaved.price * item.quantity)
                    objectResult.total = priceCalculated
                    objectResult.status = ObjectStatus.PENDING
                    objectResult.order  = order
                    objectSaved.user = userAuthenticated
                    total += priceCalculated
                    objectRepository.save(objectResult)
                }

                else -> {
                    val objectSaved = productService.getProduct(userId = userId, productId = item.identifier)
                    val objectResult: Object = parseObject(objectsToSave, Object::class.java)
                    objectResult.identifier = item.identifier
                    objectResult.type = item.type
                    objectResult.name = objectSaved.name
                    objectResult.price = objectSaved.price
                    objectResult.quantity = item.quantity
                    val priceCalculated = (objectSaved.price * item.quantity)
                    objectResult.total = priceCalculated
                    objectResult.status = ObjectStatus.PENDING
                    objectResult.order  = order
                    objectSaved.user = userAuthenticated
                    total += priceCalculated
                    objectRepository.save(objectResult)
                }
            }
        }
        return Pair(first = result?.toMutableList(), second = total)
    }

    fun getObject(
        orderId: Long,
        objectId: Long
    ): Object {
        val objectSalved: Object? = objectRepository.findObjectById(orderId = orderId, objectId = objectId)
        if (objectSalved != null) {
            return objectSalved
        } else {
            throw ResourceNotFoundException(OBJECT_NOT_FOUND)
        }
    }

    @Transactional
    fun updateStatusObject(
        orderId: Long,
        objectId: Long,
        status: ObjectStatus? = null
    ) {
        objectRepository.updateStatusObject(
            orderId = orderId,
            objectId = objectId,
            status = status
        )
    }

    @Transactional
    fun incrementMoreItemsObject(
        orderId: Long,
        objectId: Long,
        quantity: Int,
        total: Double
    ) {
        objectRepository.incrementMoreItemsObject(
            orderId = orderId,
            objectId = objectId,
            quantity = quantity,
            total = total
        )
    }

    @Transactional
    fun decrementItemsObject(
        orderId: Long,
        objectId: Long,
        quantity: Int,
        total: Double
    ) {
        objectRepository.decrementItemsObject(orderId = orderId, objectId = objectId, quantity = quantity, total = total)
    }

    @Transactional
    fun deleteObject(
        orderId: Long,
        objectId: Long
    ) {
        objectRepository.deleteObjectById(orderId = orderId, objectId = objectId)
    }

    companion object {
        const val OBJECT_NOT_FOUND = "Object not found!"
    }
}
