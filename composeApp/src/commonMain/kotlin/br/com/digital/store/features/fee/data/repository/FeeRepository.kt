package br.com.digital.store.features.fee.data.repository

import br.com.digital.store.features.fee.data.dto.CreateFeeRequestDTO
import br.com.digital.store.features.fee.data.dto.DayRequestDTO
import br.com.digital.store.features.fee.data.dto.FeeResponseDTO
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface FeeRepository {
    fun findAllFees(): Flow<ObserveNetworkStateHandler<List<FeeResponseDTO>>>
    fun createNewFee(fee: CreateFeeRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun addDaysFee(
        id: Long,
        daysOfWeek: DayRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>>
}
