package br.com.dashboard.company.service

import br.com.dashboard.company.entities.category.Category
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

    @Transactional(readOnly = true)
    fun findAllCategories(
        pageable: Pageable
    ): Page<CategoryResponseVO> {
        val categories: Page<Category>? = categoryRepository.findAllCategories(pageable = pageable)
        return categories?.map { category -> parseObject(category, CategoryResponseVO::class.java) }
            ?: throw ResourceNotFoundException(message = CATEGORY_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun findCategoryById(
        id: Long
    ): CategoryResponseVO {
        val category = getCategory(id = id)
        return parseObject(category, CategoryResponseVO::class.java)
    }

    fun getCategory(id: Long): Category {
        return categoryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(CATEGORY_NOT_FOUND) }
    }

    fun converterCategories(
        categories: MutableList<CategoryResponseVO>? = null
    ): MutableList<Category>? {
        val result = categories?.map { getCategory(id = it.id) }?.toMutableList()
        return result
    }

    fun createNewCategory(
        category: CategoryResponseVO
    ): CategoryResponseVO {
        if (!checkNameCategoryAlreadyExists(category.name)) {
            val categoryResult: Category = parseObject(category, Category::class.java)
            return parseObject(categoryRepository.save(categoryResult), CategoryResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_CATEGORY)
        }
    }

    private fun checkNameCategoryAlreadyExists(
        name: String
    ): Boolean {
        val categoryResult = categoryRepository.checkNameCategoryAlreadyExists(name = name)
        return categoryResult != null
    }

    fun updateCategory(
        category: CategoryResponseVO
    ): CategoryResponseVO {
        if (!checkNameCategoryAlreadyExists(category.name)) {
            val categoryResult: Category = getCategory(id = category.id)
            categoryResult.name = category.name
            return parseObject(categoryRepository.save(categoryResult), CategoryResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_CATEGORY)

        }
    }

    fun deleteCategory(id: Long) {
        val category = getCategory(id = id)
        categoryRepository.delete(category)
    }

    companion object {
        const val CATEGORY_NOT_FOUND = "Category not found!"
        const val DUPLICATE_NAME_CATEGORY = "The category already exists"
    }
}
