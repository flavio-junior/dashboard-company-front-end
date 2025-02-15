package br.com.digital.store.features.settings.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.settings.data.dto.VersionResponseDTO
import br.com.digital.store.features.settings.data.repository.SettingsRepository
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _checkUpdates =
        mutableStateOf<ObserveNetworkStateHandler<VersionResponseDTO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val checkUpdates: State<ObserveNetworkStateHandler<VersionResponseDTO>> =
        _checkUpdates

    fun checkUpdates(version: String) {
        viewModelScope.launch {
            repository.checkUpdates(version = version)
                .onStart {
                    _checkUpdates.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _checkUpdates.value = ObserveNetworkStateHandler.Success(s = it.result)
                }
        }
    }
}
