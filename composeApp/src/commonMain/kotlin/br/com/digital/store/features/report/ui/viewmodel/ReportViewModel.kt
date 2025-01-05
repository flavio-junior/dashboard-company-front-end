package br.com.digital.store.features.report.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.report.data.repository.ReportRepository
import br.com.digital.store.features.report.data.vo.PaymentsResponseVO
import br.com.digital.store.features.report.domain.converter.ConverterReport
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReportViewModel(
    private val repository: ReportRepository,
    private val converter: ConverterReport
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllReports =
        mutableStateOf<ObserveNetworkStateHandler<PaymentsResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllReports: State<ObserveNetworkStateHandler<PaymentsResponseVO>> =
        _findAllReports

    var showEmptyList = mutableStateOf(value = true)
    
    fun findAllReports(
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
            repository.findAllPayments(
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllReports.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted = converter.converterContentDTOToVO(content = response)
                        if (objectConverted.content.isNotEmpty()) {
                            showEmptyList.value = false
                            _findAllReports.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        } else {
                            _findAllReports.value = ObserveNetworkStateHandler.Success(
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
        val lastPage = _findAllReports.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllReports()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllReports()
        }
    }
}
