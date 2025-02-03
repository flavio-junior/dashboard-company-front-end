package br.com.digital.store.features.payment.data.repository

import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.payment.data.dto.PaymentsResponseDTO
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun findAllPayments(
        code: String = EMPTY_TEXT,
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<PaymentsResponseDTO>>
}