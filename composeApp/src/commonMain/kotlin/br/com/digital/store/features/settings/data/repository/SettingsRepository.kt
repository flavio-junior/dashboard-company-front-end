package br.com.digital.store.features.settings.data.repository

import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.settings.data.dto.VersionResponseDTO
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun checkUpdates(version: String): Flow<ObserveNetworkStateHandler<VersionResponseDTO>>
}
