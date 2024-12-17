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

    @Autowired
    private lateinit var userService: UserService

    @Transactional(readOnly = true)
    fun findAllFoods(
        user: User,
        foodName: String?,
        pageable: Pageable
    ): Page<FoodResponseVO> {
        val foods: Page<Food>? = foodRepository.findAllFoods(userId = user.id, foodName = foodName, pageable = pageable)
        return foods?.map { food -> parseObject(food, FoodResponseVO::class.java) }
            ?: throw ResourceNotFoundException(message = PRODUCT_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun findFoodByName(
        user: User,
        name: String
    ): List<FoodResponseVO> {
        val foods: List<Food> = foodRepository.findFoodByName(userId = user.id, foodName = name)
        return foods.map { food -> parseObject(food, FoodResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findFoodById(
        user: User,
        foodId: Long
    ): FoodResponseVO {
        val food = getFood(userId = user.id, foodId = foodId)
        return parseObject(food, FoodResponseVO::class.java)
    }

    fun getFood(
        userId: Long,
        foodId: Long
    ): Food {
        val foodSaved: Food? = foodRepository.findFoodById(userId = userId, foodId = foodId)
        if (foodSaved != null) {
            return foodSaved
        } else {
            throw ResourceNotFoundException(FOOD_NOT_FOUND)
        }
    }

    @Transactional
    fun createNewFood(
        user: User,
        food: FoodRequestVO
    ): FoodResponseVO {
        if (!checkFoodNameAlreadyExists(userId = user.id, foodName = food.name)) {
            val userAuthenticated = userService.findUserById(id = user.id)
            val foodResult: Food = parseObject(food, Food::class.java)
            foodResult.categories = categoryService.converterCategories(userId = user.id, categories = food.categories)
            foodResult.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            foodResult.user = userAuthenticated
            return parseObject(foodRepository.save(foodResult), FoodResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_FOOD)
        }
    }

    private fun checkFoodNameAlreadyExists(
        userId: Long,
        foodName: String
    ): Boolean {
        val foodResult = foodRepository.checkNameFoodAlreadyExists(userId = userId, foodName = foodName)
        return foodResult != null
    }

    @Transactional
    fun updateFood(
        user: User,
        food: FoodResponseVO
    ): FoodResponseVO {
        if (!checkFoodNameAlreadyExists(userId = user.id, foodName = food.name)) {
            val foodSaved: Food = getFood(userId = user.id, foodId = food.id)
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
        user: User,
        foodId: Long,
        price: PriceRequestVO
    ) {
        val foodSaved = getFood(userId = user.id, foodId = foodId)
        foodRepository.updatePriceFood(userId = user.id, idFood = foodSaved.id, price = price.price)
    }

    @Transactional
    fun deleteFood(
        user: User,
        foodId: Long
    ) {
        val foodSaved = getFood(userId = user.id, foodId = foodId)
        foodSaved.categories = null
        foodRepository.deleteFoodById(userId = user.id, foodId = foodId)
    }

    companion object {
        const val FOOD_NOT_FOUND = "Food not found!"
        const val DUPLICATE_NAME_FOOD = "The Food already exists"
    }
}
