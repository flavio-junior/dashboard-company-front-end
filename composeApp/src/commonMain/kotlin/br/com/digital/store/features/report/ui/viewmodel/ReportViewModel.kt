package br.com.digital.store.features.report.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.report.data.dto.ReportRequestDTO
import br.com.digital.store.features.report.data.repository.ReportRepository
import br.com.digital.store.features.report.data.vo.ReportsResponseVO
import br.com.digital.store.features.report.domain.ConverterReport
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReportViewModel(
    val repository: ReportRepository,
    val converter: ConverterReport
): ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllReports =
        mutableStateOf<ObserveNetworkStateHandler<ReportsResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllReports: State<ObserveNetworkStateHandler<ReportsResponseVO>> =
        _findAllReports

    var showEmptyList = mutableStateOf(value = true)

    private val _createReport =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createReport: State<ObserveNetworkStateHandler<Unit>> = _createReport

    private val _deleteReport =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteReport: State<ObserveNetworkStateHandler<Unit>> = _deleteReport

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
            repository.findAllReports(
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

    fun createReport(report: ReportRequestDTO) {
        viewModelScope.launch {
            repository.createNewReport(report = report)
                .onStart {
                    _createReport.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createReport.value = it
                }
        }
    }

    fun deleteReport(reportId: Long) {
        viewModelScope.launch {
            repository.deleteReport(reportId = reportId)
                .onStart {
                    _deleteReport.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteReport.value = it
                }
        }
    }

    fun resetReport(reset: ResetReport) {
        when (reset) {
            ResetReport.CREATE_REPORT -> {
                _createReport.value = ObserveNetworkStateHandler.Loading(l = false)
            }
            ResetReport.DELETE_REPORT -> {
                _deleteReport.value = ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetReport {
    CREATE_REPORT,
    DELETE_REPORT
}
