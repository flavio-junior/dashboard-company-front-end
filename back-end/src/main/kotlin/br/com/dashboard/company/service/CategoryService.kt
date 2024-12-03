package br.com.dashboard.company.service

import br.com.dashboard.company.entities.category.Category
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.CategoryRepository
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.category.CategoryResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var userService: UserService

    @Transactional(readOnly = true)
    fun findAllCategories(
        user: User,
        name: String?,
        pageable: Pageable
    ): Page<CategoryResponseVO> {
        val categories: Page<Category> =
            categoryRepository.findAllCategories(userId = user.id, name = name, pageable = pageable)
        return categories.map { category -> parseObject(category, CategoryResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findCategoryByName(
        user: User,
        name: String
    ): List<CategoryResponseVO> {
        val products: List<Category> = categoryRepository.findCategoryByName(userId = user.id, name = name)
        return products.map { product -> parseObject(product, CategoryResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findCategoryById(
        user: User,
        categoryId: Long
    ): CategoryResponseVO {
        val category = getCategory(categoryId = categoryId, userId = user.id)
        return parseObject(category, CategoryResponseVO::class.java)
    }

    fun getCategory(
        userId: Long,
        categoryId: Long
    ): Category {
        val categorySaved: Category? = categoryRepository.findCategoryById(userId = userId, categoryId = categoryId)
        if (categorySaved != null) {
            return categorySaved
        } else {
            throw ResourceNotFoundException(message = CATEGORY_NOT_FOUND)
        }
    }

    fun converterCategories(
        userId: Long,
        categories: MutableList<CategoryResponseVO>? = null
    ): MutableList<Category>? {
        val result = categories?.map { category ->
            getCategory(categoryId = category.id, userId = userId)
        }?.toMutableList()
        return result
    }

    @Transactional
    fun createNewCategory(
        user: User,
        category: CategoryResponseVO
    ): CategoryResponseVO {
        if (!checkNameCategoryAlreadyExists(id = user.id, name = category.name)) {
            val userAuthenticated = userService.findUserById(id = user.id)
            val categoryResult: Category = parseObject(category, Category::class.java)
            categoryResult.user = userAuthenticated
            return parseObject(categoryRepository.save(categoryResult), CategoryResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_CATEGORY)
        }
    }

    private fun checkNameCategoryAlreadyExists(
        id: Long,
        name: String
    ): Boolean {
        val categoryResult = categoryRepository.checkNameCategoryAlreadyExists(userId = id, name = name)
        return categoryResult != null
    }

    fun updateCategory(
        user: User,
        category: CategoryResponseVO
    ): CategoryResponseVO {
        if (!checkNameCategoryAlreadyExists(id = user.id, name = category.name)) {
            val categoryResult: Category = getCategory(userId = user.id, categoryId = category.id)
            categoryResult.name = category.name
            return parseObject(categoryRepository.save(categoryResult), CategoryResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_CATEGORY)

        }
    }

    @Transactional
    fun deleteCategory(
        userId: Long,
        categoryId: Long
    ) {
        val category = getCategory(userId = userId, categoryId = categoryId)
        categoryRepository.deleteCategoryById(categoryId = category.id, userId = userId)
    }

    companion object {
        const val CATEGORY_NOT_FOUND = "Category not found!"
        const val DUPLICATE_NAME_CATEGORY = "The category already exists"
    }
}
