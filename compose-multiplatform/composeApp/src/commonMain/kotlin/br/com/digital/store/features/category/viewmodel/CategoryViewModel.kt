package br.com.digital.store.features.category.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.common.category.dto.CategoryRequestDTO
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.features.category.data.CategoryRepository
import br.com.digital.store.features.category.domain.ConverterCategory
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository,
    private val converter: ConverterCategory
) : ViewModel() {

    private val _findAllCategories =
        mutableStateOf<ObserveNetworkStateHandler<List<CategoryResponseVO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllCategories: State<ObserveNetworkStateHandler<List<CategoryResponseVO>>> =
        _findAllCategories

    private val _createNewCategory =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createNewCategory: State<ObserveNetworkStateHandler<Unit>> = _createNewCategory

    fun findAllCategories() {
        viewModelScope.launch {
            repository.findAllCategories()
                .onStart {
                    _findAllCategories.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        _findAllCategories.value = ObserveNetworkStateHandler.Success(
                            s = converter.converterCategoriesResponseDTOToVO(categories = response)
                        )
                    }
                }
        }
    }

    fun createCategory(category: String) {
        viewModelScope.launch {
            repository.createNewCategory(category = CategoryRequestDTO(name = category))
                .onStart {
                    _createNewCategory.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createNewCategory.value = it
                }
        }
    }
}
