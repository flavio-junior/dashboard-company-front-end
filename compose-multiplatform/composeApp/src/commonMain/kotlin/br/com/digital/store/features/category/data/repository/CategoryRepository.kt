package br.com.digital.store.features.category.data.repository

import br.com.digital.store.features.category.data.dto.CategoriesResponseDTO
import br.com.digital.store.features.category.data.dto.CategoryRequestDTO
import br.com.digital.store.features.category.data.dto.EditCategoryRequestDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
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
    fun createNewCategory(category: CategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun editCategory(category: EditCategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteCategory(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
