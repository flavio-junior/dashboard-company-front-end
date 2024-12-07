package br.com.dashboard.company.service

import br.com.dashboard.company.entities.`object`.Object
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.ObjectRepository
import br.com.dashboard.company.repository.OrderObjectRepository
import br.com.dashboard.company.utils.common.Action
import br.com.dashboard.company.utils.common.TypeItem
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.`object`.ObjectRequestVO
import br.com.dashboard.company.vo.`object`.UpdateObjectRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ObjectService {

    @Autowired
    private lateinit var objectRepository: ObjectRepository

    @Autowired
    private lateinit var orderObjectRepository: OrderObjectRepository

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var foodService: FoodService

    @Transactional
    fun saveObjects(
        userId: Long,
        objectsToSave: MutableList<ObjectRequestVO>? = null
    ): Pair<MutableList<Object>?, Double> {
        var total = 0.0
        val result = objectsToSave?.map { item ->
            if (item.type == TypeItem.FOOD) {
                val objectSaved = foodService.getFood(userId = userId, foodId = item.identifier)
                val objectResult: Object = parseObject(objectsToSave, Object::class.java)
                objectResult.identifier = item.identifier
                objectResult.type = item.type
                objectResult.name = objectSaved.name
                objectResult.price = objectSaved.price
                objectResult.quantity = item.quantity
                val priceCalculated = (objectSaved.price * item.quantity)
                objectResult.total = priceCalculated
                total = priceCalculated
                objectRepository.save(objectResult)
            } else {
                val objectSaved = productService.getProduct(userId = userId, productId = item.identifier)
                val objectResult: Object = parseObject(objectsToSave, Object::class.java)
                objectResult.identifier = item.identifier
                objectResult.type = item.type
                objectResult.name = objectSaved.name
                objectResult.price = objectSaved.price
                objectResult.quantity = item.quantity
                val priceCalculated = (objectSaved.price * item.quantity)
                objectResult.total = priceCalculated
                total = priceCalculated
                objectRepository.save(objectResult)
            }
        }
        return Pair(first = result?.toMutableList(), second = total)
    }

    private fun getOrder(id: Long): Object {
        return objectRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(OBJECT_NOT_FOUND) }
    }

    @Transactional
    fun updateObject(
        idOrder: Long,
        idObject: Long,
        objectActual: UpdateObjectRequestVO
    ) {
        val objectSaved = getOrder(id = idObject)
        val priceCalculated = (objectSaved.price * objectActual.quantity)
        when (objectActual.action) {
            Action.ADD_ITEM ->
                objectRepository.updateObject(id = idObject, quantity = objectActual.quantity, total = priceCalculated)

            Action.REMOVE_ITEM ->
                objectRepository.removeItemObject(
                    id = idObject,
                    quantity = objectActual.quantity,
                    total = priceCalculated
                )

            Action.REMOVE_OBJECT -> deleteObject(idOrder = idOrder, idObject = idObject)
        }
    }

    @Transactional
    fun deleteObject(
        idOrder: Long,
        idObject: Long
    ) {
        orderObjectRepository.deleteRelationBetween(idOrder = idOrder, idObject = idObject)
        objectRepository.delete(getOrder(id = idObject))
    }

    companion object {
        const val OBJECT_NOT_FOUND = "Object not found!"
    }
}
