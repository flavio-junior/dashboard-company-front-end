package br.com.digital.store.features.category.data.repository

import br.com.digital.store.features.category.data.dto.CategoriesResponseDTO
import br.com.digital.store.features.category.data.dto.CategoryNameRequestDTO
import br.com.digital.store.features.category.data.dto.CategoryRequestDTO
import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import br.com.digital.store.features.category.data.dto.EditCategoryRequestDTO
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun findAllCategories(
        name: String = EMPTY_TEXT,
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<CategoriesResponseDTO>>

    fun finCategoryByName(
        name: CategoryNameRequestDTO
    ): Flow<ObserveNetworkStateHandler<List<CategoryResponseDTO>>>

    fun createNewCategory(category: CategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun editCategory(category: EditCategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteCategory(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
