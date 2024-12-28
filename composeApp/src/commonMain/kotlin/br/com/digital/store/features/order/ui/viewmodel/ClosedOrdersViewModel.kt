package br.com.digital.store.features.order.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.repository.OrderRepository
import br.com.digital.store.features.order.data.vo.OrdersResponseVO
import br.com.digital.store.features.order.domain.converter.ConverterOrder
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ClosedOrdersViewModel(
    private val repository: OrderRepository,
    private val converter: ConverterOrder
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    var showEmptyList = mutableStateOf(value = true)

    private val _findAllClosedOrders =
        mutableStateOf<ObserveNetworkStateHandler<OrdersResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllClosedOrders: State<ObserveNetworkStateHandler<OrdersResponseVO>> =
        _findAllClosedOrders

    fun findAllClosedOrders(
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
            repository.findAllClosedOrders(
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllClosedOrders.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted = converter.converterContentDTOToVO(content = response)
                        if (objectConverted.content?.isNotEmpty() == true) {
                            showEmptyList.value = false
                            _findAllClosedOrders.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        } else {
                            _findAllClosedOrders.value = ObserveNetworkStateHandler.Success(
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
        val lastPage = _findAllClosedOrders.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllClosedOrders()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllClosedOrders()
        }
    }
}
