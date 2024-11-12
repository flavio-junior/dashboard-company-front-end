package br.com.dashboard.company.service

import br.com.dashboard.company.repository.CategoryRepository
import br.com.dashboard.company.utils.ConverterUtils.parseObject
import br.com.dashboard.company.vo.category.CategoryResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Transactional(readOnly = true)
    fun findAllCategories(): List<CategoryResponseVO> {
        val categories = categoryRepository.findAll()
        return categories.map { category -> parseObject(category, CategoryResponseVO::class.java) }
    }
}
