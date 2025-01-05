package br.com.digital.store.features.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.dto.AddressRequestDTO
import br.com.digital.store.features.order.data.repository.OrderRepository
import br.com.digital.store.features.order.data.vo.OrdersResponseVO
import br.com.digital.store.features.order.domain.converter.ConverterOrder
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OpenOrdersViewModel(
    private val repository: OrderRepository,
    private val converter: ConverterOrder
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllOpenOrders =
        mutableStateOf<ObserveNetworkStateHandler<OrdersResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllOpenOrders: State<ObserveNetworkStateHandler<OrdersResponseVO>> =
        _findAllOpenOrders

    private val _updateAddressOrder =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateAddressOrder: State<ObserveNetworkStateHandler<Unit>> = _updateAddressOrder

    var showEmptyList = mutableStateOf(value = true)

    fun findAllOpenOrders(
        sort: String = this.sort,
        size: Int = this.sizeDefault,
        route: LocationRoute = LocationRoute.SEARCH
    ) {
        when (route) {
            LocationRoute.SEARCH, LocationRoute.SORT, LocationRoute.RELOAD -> {}
            LocationRoute.FILTER -> {
                this.currentPage = NUMBER_ZERO
                showEmptyList.value = false
            }
        }
        viewModelScope.launch {
            sizeDefault = size
            repository.findAllOpenOrders(
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllOpenOrders.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted = converter.converterContentDTOToVO(content = response)
                        if (objectConverted.content?.isNotEmpty() == true) {
                            showEmptyList.value = false
                            _findAllOpenOrders.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        } else {
                            _findAllOpenOrders.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        }
                    }
                }
        }
    }

    fun showEmptyList(show: Boolean) {
        showEmptyList.value = show
    }

    fun loadNextPage() {
        val lastPage = _findAllOpenOrders.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllOpenOrders()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllOpenOrders()
        }
    }

    fun updateAddressOrder(
        orderId: Long,
        addressId: Long,
        updateAddress: AddressRequestDTO
    ) {
        viewModelScope.launch {
            repository.updateAddressOrder(
                orderId = orderId,
                addressId = addressId,
                updateAddress = updateAddress
            ).onStart {
                _updateAddressOrder.value = ObserveNetworkStateHandler.Loading(l = true)
            }.collect {
                _updateAddressOrder.value = it
            }
        }
    }

    fun resetOpenOrders(reset: ResetOpenOrders) {
        when (reset) {
            ResetOpenOrders.UPDATE_ADDRESS_ORDER -> {
                _updateAddressOrder.value = ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetOpenOrders {
    UPDATE_ADDRESS_ORDER
}
