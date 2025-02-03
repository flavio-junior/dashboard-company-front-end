package br.com.digital.store.features.resume.data.repository

import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.resume.data.dto.AnaliseDayDTO
import br.com.digital.store.features.resume.data.dto.TypeAnalysisRequestDTO
import kotlinx.coroutines.flow.Flow

interface ResumeRepository {
    fun getDetailsOfAnalysis(
        date: String,
        type: TypeAnalysisRequestDTO
    ): Flow<ObserveNetworkStateHandler<AnaliseDayDTO>>
}
