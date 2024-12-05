package br.com.digital.store.features.food.data.repository

import br.com.digital.store.features.food.data.dto.FoodRequestDTO
import br.com.digital.store.features.food.data.dto.FoodsResponseDTO
import br.com.digital.store.features.food.data.dto.UpdateFoodRequestDTO
import br.com.digital.store.features.food.data.dto.UpdatePriceFoodRequestDTO
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun findAllFoods(
        name: String = EMPTY_TEXT,
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<FoodsResponseDTO>>
    fun createNewFood(food: FoodRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updateFood(food: UpdateFoodRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updatePriceFood(id: Long, price: UpdatePriceFoodRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteFood(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
