package br.com.digital.store.features.category.data

import br.com.digital.store.common.category.dto.CategoryRequestDTO
import br.com.digital.store.common.category.dto.CategoryResponseDTO
import br.com.digital.store.common.category.dto.EditCategoryRequestDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun findAllCategories(): Flow<ObserveNetworkStateHandler<List<CategoryResponseDTO>>>
    fun createNewCategory(category: CategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun editCategory(category: EditCategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteCategory(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
