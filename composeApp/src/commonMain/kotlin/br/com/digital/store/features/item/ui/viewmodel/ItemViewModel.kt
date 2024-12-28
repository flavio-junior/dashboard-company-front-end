package br.com.digital.store.features.item.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.features.item.data.dto.EditItemRequestDTO
import br.com.digital.store.features.item.data.dto.ItemRequestDTO
import br.com.digital.store.features.item.data.dto.ItemResponseDTO
import br.com.digital.store.features.item.data.dto.UpdatePriceItemRequestDTO
import br.com.digital.store.features.item.data.repository.ItemRepository
import br.com.digital.store.features.item.data.vo.ItemsResponseVO
import br.com.digital.store.features.item.domain.ConverterItem
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ItemViewModel(
    private val repository: ItemRepository,
    private val converter: ConverterItem
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllItems =
        mutableStateOf<ObserveNetworkStateHandler<ItemsResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllItems: State<ObserveNetworkStateHandler<ItemsResponseVO>> =
        _findAllItems

    private val _findItemByName =
        mutableStateOf<ObserveNetworkStateHandler<List<ItemResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findItemByName: State<ObserveNetworkStateHandler<List<ItemResponseDTO>>> =
        _findItemByName

    private val _createNewItem =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createNewItem: State<ObserveNetworkStateHandler<Unit>> = _createNewItem

    private val _editItem =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val editItem: State<ObserveNetworkStateHandler<Unit>> = _editItem

    private val _updatePriceItem =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updatePriceItem: State<ObserveNetworkStateHandler<Unit>> = _updatePriceItem

    private val _deleteItem =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteItem: State<ObserveNetworkStateHandler<Unit>> = _deleteItem

    fun findAllItems(
        name: String = EMPTY_TEXT,
        sort: String = this.sort,
        size: Int = this.sizeDefault,
        route: LocationRoute = LocationRoute.SEARCH
    ) {
        when (route) {
            LocationRoute.SEARCH, LocationRoute.SORT, LocationRoute.RELOAD -> {}
            LocationRoute.FILTER -> {
                this.currentPage = NUMBER_ZERO
            }
        }
        viewModelScope.launch {
            sizeDefault = size
            repository.findAllItems(
                name = name,
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllItems.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        _findAllItems.value = ObserveNetworkStateHandler.Success(
                            s = converter.converterContentDTOToVO(content = response)
                        )
                    }
                }
        }
    }

    fun loadNextPage() {
        val lastPage = _findAllItems.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllItems()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllItems()
        }
    }

    fun findItemByName(name: String) {
        viewModelScope.launch {
            repository.findItemByName(name = name)
                .onStart {
                    _findItemByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findItemByName.value = it
                }
        }
    }

    fun createItem(name: String, price: Double) {
        viewModelScope.launch {
            repository.createNewItem(item = ItemRequestDTO(name = name, price = price))
                .onStart {
                    _createNewItem.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createNewItem.value = it
                }
        }
    }

    fun editItem(item: EditItemRequestDTO) {
        viewModelScope.launch {
            repository.editItem(item = item)
                .onStart {
                    _editItem.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _editItem.value = it
                }
        }
    }

    fun updatePriceItem(id: Long, item: UpdatePriceItemRequestDTO) {
        viewModelScope.launch {
            repository.updatePriceItem(id = id, price = item)
                .onStart {
                    _updatePriceItem.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updatePriceItem.value = it
                }
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch {
            repository.deleteItem(id = id)
                .onStart {
                    _deleteItem.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteItem.value = it
                }
        }
    }

    fun resetItem(reset: ResetItem) {
        when(reset) {
            ResetItem.FIND_BY_NAME -> {
                _findItemByName.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetItem.CREATE_ITEM -> {
                _createNewItem.value = ObserveNetworkStateHandler.Loading(l = false)
                findAllItems()
            }
        }
    }
}

enum class ResetItem {
    FIND_BY_NAME,
    CREATE_ITEM
}
