package br.com.digital.store.features.category.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.common.category.dto.CategoryRequestDTO
import br.com.digital.store.common.category.dto.EditCategoryRequestDTO
import br.com.digital.store.common.category.vo.CategoriesResponseVO
import br.com.digital.store.features.category.data.CategoryRepository
import br.com.digital.store.features.category.domain.ConverterCategory
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.strings.StringsUtils.ASC
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository,
    private val converter: ConverterCategory
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sort: String = ASC

    private val _findAllCategories =
        mutableStateOf<ObserveNetworkStateHandler<CategoriesResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllCategories: State<ObserveNetworkStateHandler<CategoriesResponseVO>> =
        _findAllCategories

    private val _createNewCategory =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createNewCategory: State<ObserveNetworkStateHandler<Unit>> = _createNewCategory

    private val _editCategory =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val editCategory: State<ObserveNetworkStateHandler<Unit>> = _editCategory

    private val _deleteCategory =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteCategory: State<ObserveNetworkStateHandler<Unit>> = _deleteCategory

    fun findAllCategories(name: String = "", sort: String = this.sort) {
        viewModelScope.launch {
            repository.findAllCategories(name = name, page = currentPage, sort = sort)
                .onStart {
                    _findAllCategories.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        _findAllCategories.value = ObserveNetworkStateHandler.Success(
                            s = converter.converterContentDTOToVO(content = response)
                        )
                    }
                }
        }
    }

    fun loadNextPage() {
        val lastPage = _findAllCategories.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllCategories()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllCategories()
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

    fun editCategory(category: EditCategoryRequestDTO) {
        viewModelScope.launch {
            repository.editCategory(category = category)
                .onStart {
                    _editCategory.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _editCategory.value = it
                }
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            repository.deleteCategory(id = id)
                .onStart {
                    _deleteCategory.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteCategory.value = it
                }
        }
    }
}
