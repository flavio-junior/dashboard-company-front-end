package br.com.digital.store.features.resume.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.DAY
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.resume.data.dto.TypeAnalysisRequestDTO
import br.com.digital.store.features.resume.data.repository.ResumeRepository
import br.com.digital.store.features.resume.data.vo.AnaliseDayVO
import br.com.digital.store.features.resume.domain.converter.ConverterResume
import br.com.digital.store.features.resume.domain.type.TypeAnalysis
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ResumeViewModel(
    private val repository: ResumeRepository,
    private val converterResume: ConverterResume
) : ViewModel() {

    var analiseDay = DAY

    private val _getAnalysisDay =
        mutableStateOf<ObserveNetworkStateHandler<AnaliseDayVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val getAnalysisDay: State<ObserveNetworkStateHandler<AnaliseDayVO>> =
        _getAnalysisDay

    fun updateAnaliseDay(
        label: String
    ) {
        analiseDay = label
    }

    fun getDetailsOfAnalysis(
        startDate: String? = null,
        endDate: String? = null,
        type: TypeAnalysis = TypeAnalysis.DAY
    ) {
        if (startDate != null) {
            updateAnaliseDay(label = DAY)
        }
        viewModelScope.launch {
            repository.getDetailsOfAnalysis(
                startDate = startDate,
                type = TypeAnalysisRequestDTO(type = type)
            )
                .onStart {
                    _getAnalysisDay.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted =
                            converterResume.converterAnaliseDayDTOToVO(content = response)
                        _getAnalysisDay.value = ObserveNetworkStateHandler.Success(
                            s = objectConverted
                        )
                    }
                }
        }
    }
}
