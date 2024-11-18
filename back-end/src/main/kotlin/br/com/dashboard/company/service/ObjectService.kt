package br.com.dashboard.company.service

import br.com.dashboard.company.entities.`object`.Object
import br.com.dashboard.company.repository.ObjectRepository
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
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var foodService: FoodService

    @Transactional
    fun saveObject(
        objectsToSave: MutableList<ObjectRequestVO>? = null
    ): Pair<MutableList<Object>?, Double> {
        var total = 0.0
        val result = objectsToSave?.map { item ->
            if (item.type == TypeItem.FOOD) {
                val objectSaved = foodService.getFood(id = item.identifier)
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
                val objectSaved = productService.getProduct(id = item.identifier)
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
}
