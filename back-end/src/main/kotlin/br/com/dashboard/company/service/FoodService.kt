package br.com.dashboard.company.service

import br.com.dashboard.company.entities.food.Food
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.FoodRepository
import br.com.dashboard.company.service.ProductService.Companion.PRODUCT_NOT_FOUND
import br.com.dashboard.company.utils.common.PriceRequestVO
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.food.FoodRequestVO
import br.com.dashboard.company.vo.food.FoodResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class FoodService {

    @Autowired
    private lateinit var foodRepository: FoodRepository

    @Autowired
    private lateinit var categoryService: CategoryService

    @Transactional(readOnly = true)
    fun findAllFoods(
        name: String?,
        pageable: Pageable
    ): Page<FoodResponseVO> {
        val foods: Page<Food>? = foodRepository.findAllFoods(name = name, pageable = pageable)
        return foods?.map { food -> parseObject(food, FoodResponseVO::class.java) }
        ?: throw ResourceNotFoundException(message = PRODUCT_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun findFoodByName(
        name: String
    ) : List<FoodResponseVO> {
        val foods: List<Food> = foodRepository.findFoodByName(name)
        return foods.map { food -> parseObject(food, FoodResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findFoodById(
        id: Long
    ): FoodResponseVO {
        val food = getFood(id = id)
        return parseObject(food, FoodResponseVO::class.java)
    }

    fun getFood(id: Long): Food {
        return foodRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(FOOD_NOT_FOUND) }
    }

    @Transactional
    fun createNewFood(
        user: User,
        food: FoodRequestVO
    ): FoodResponseVO {
        if (!checkNameFoodAlreadyExists(name = food.name)) {
            val foodResult: Food = parseObject(food, Food::class.java)
            foodResult.categories = categoryService.converterCategories(userId = user.id, categories = food.categories)
            foodResult.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            return parseObject(foodRepository.save(foodResult), FoodResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_FOOD)
        }
    }

    private fun checkNameFoodAlreadyExists(name: String): Boolean {
        val foodResult = foodRepository.checkNameFoodAlreadyExists(name = name)
        return foodResult != null
    }

    @Transactional
    fun updateFood(
        user: User,
        food: FoodResponseVO
    ): FoodResponseVO {
        if (!checkNameFoodAlreadyExists(name = food.name)) {
            val foodSaved: Food = getFood(id = food.id)
            foodSaved.name = food.name
            foodSaved.categories?.clear()
            foodSaved.categories = categoryService.converterCategories(userId = user.id, categories = food.categories)
            foodSaved.price = food.price
            return parseObject(foodRepository.save(foodSaved), FoodResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_FOOD)
        }
    }

    @Transactional
    fun updatePriceFood(
        idFood: Long,
        price: PriceRequestVO
    ) {
        getFood(id = idFood)
        foodRepository.updatePriceFood(idFood = idFood, price = price.price)
    }

    fun deleteFood(id: Long) {
        val food = getFood(id = id)
        food.categories = null
        foodRepository.delete(food)
    }

    companion object {
        const val FOOD_NOT_FOUND = "Food not found!"
        const val DUPLICATE_NAME_FOOD = "The Food already exists"
    }
}
