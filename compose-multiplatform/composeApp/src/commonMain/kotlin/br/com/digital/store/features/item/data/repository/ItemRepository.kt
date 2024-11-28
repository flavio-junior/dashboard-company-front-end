package br.com.digital.store.features.item.data.repository

import br.com.digital.store.features.item.data.dto.EditItemRequestDTO
import br.com.digital.store.features.item.data.dto.ItemRequestDTO
import br.com.digital.store.features.item.data.dto.ItemsResponseDTO
import br.com.digital.store.common.item.dto.UpdatePriceItemRequestDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun findAllItems(
        name: String = EMPTY_TEXT,
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ItemsResponseDTO>>
    fun createNewItem(item: ItemRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun editItem(item: EditItemRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updatePriceItem(id: Long, price: UpdatePriceItemRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteItem(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
