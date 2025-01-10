package br.com.digital.store.features.resume.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.resume.data.repository.ResumeRepository
import br.com.digital.store.features.resume.data.vo.AnaliseDayVO
import br.com.digital.store.features.resume.domain.converter.ConverterResume
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ResumeViewModel(
    private val repository: ResumeRepository,
    private val converterResume: ConverterResume
) : ViewModel() {

    private val _getAnalysisDay =
        mutableStateOf<ObserveNetworkStateHandler<AnaliseDayVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val getAnalysisDay: State<ObserveNetworkStateHandler<AnaliseDayVO>> =
        _getAnalysisDay

    fun getAnalysisDay(
        date: String? = null
    ) {
        viewModelScope.launch {
            repository.getAnalysisDay(date = date ?: EMPTY_TEXT)
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
