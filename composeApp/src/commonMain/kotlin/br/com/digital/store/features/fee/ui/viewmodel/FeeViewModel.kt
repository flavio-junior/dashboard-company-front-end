package br.com.digital.store.features.fee.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.fee.data.dto.DayRequestDTO
import br.com.digital.store.features.fee.data.repository.FeeRepository
import br.com.digital.store.features.fee.data.vo.FeeResponseVO
import br.com.digital.store.features.fee.domain.converter.ConverterFee
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FeeViewModel(
    private val repository: FeeRepository,
    private val converter: ConverterFee
) : ViewModel() {

    private val _findAllFees =
        mutableStateOf<ObserveNetworkStateHandler<List<FeeResponseVO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllFees: State<ObserveNetworkStateHandler<List<FeeResponseVO>>> =
        _findAllFees

    private val _addDaysOkWeek =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val addDaysOkWeek: State<ObserveNetworkStateHandler<Unit>> =
        _addDaysOkWeek

    fun findAllFees() {
        viewModelScope.launch {
            repository.findAllFees()
                .onStart {
                    _findAllFees.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted = converter.converterContentDTOToVO(content = response)
                        _findAllFees.value = ObserveNetworkStateHandler.Success(
                            s = objectConverted
                        )
                    }
                }
        }
    }

    fun addDaysOkWeek(id: Long, daysOfWeek: DayRequestDTO) {
        viewModelScope.launch {
            repository.addDaysFee(id = id, daysOfWeek = daysOfWeek)
                .onStart {
                    _addDaysOkWeek.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _addDaysOkWeek.value = ObserveNetworkStateHandler.Success(s = Unit)
                }
        }
    }

    fun resetFee() {
        _addDaysOkWeek.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
