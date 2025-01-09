package br.com.digital.store.features.resume.data.repository

import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.resume.data.dto.AnaliseDayDTO
import kotlinx.coroutines.flow.Flow

interface ResumeRepository {
    fun getAnalysisDay(
        date: String
    ): Flow<ObserveNetworkStateHandler<AnaliseDayDTO>>
}
