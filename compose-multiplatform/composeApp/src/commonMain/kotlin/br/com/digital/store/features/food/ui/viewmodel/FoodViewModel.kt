package br.com.digital.store.features.food.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.features.food.data.dto.FoodRequestDTO
import br.com.digital.store.features.food.data.dto.UpdateFoodRequestDTO
import br.com.digital.store.features.food.data.dto.UpdatePriceFoodRequestDTO
import br.com.digital.store.features.food.data.repository.FoodRepository
import br.com.digital.store.features.food.data.vo.FoodsResponseVO
import br.com.digital.store.features.food.domain.ConverterFood
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FoodViewModel(
    private val repository: FoodRepository,
    private val converter: ConverterFood
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllFoods =
        mutableStateOf<ObserveNetworkStateHandler<FoodsResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllFoods: State<ObserveNetworkStateHandler<FoodsResponseVO>> =
        _findAllFoods

    var showEmptyList = mutableStateOf(value = true)

    private val _createFood =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createFood: State<ObserveNetworkStateHandler<Unit>> = _createFood

    private val _updateFood =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateFood: State<ObserveNetworkStateHandler<Unit>> = _updateFood

    private val _updatePriceFood =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updatePriceFood: State<ObserveNetworkStateHandler<Unit>> = _updatePriceFood

    private val _restockFood =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val restockFood: State<ObserveNetworkStateHandler<Unit>> = _restockFood

    private val _deleteFood =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteFood: State<ObserveNetworkStateHandler<Unit>> = _deleteFood

    fun findAllFoods(
        name: String = EMPTY_TEXT,
        sort: String = this.sort,
        size: Int = this.sizeDefault,
        route: LocationRoute = LocationRoute.SEARCH
    ) {
        when (route) {
            LocationRoute.SEARCH, LocationRoute.SORT, LocationRoute.RELOAD -> {}
            LocationRoute.FILTER -> {
                this.currentPage = NUMBER_ZERO
                showEmptyList.value = false
            }
        }
        viewModelScope.launch {
            sizeDefault = size
            repository.findAllFoods(
                name = name,
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllFoods.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted = converter.converterContentDTOToVO(content = response)
                        if (objectConverted.content.isNotEmpty()) {
                            showEmptyList.value = false
                            _findAllFoods.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        } else {
                            _findAllFoods.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        }
                    }
                }
        }
    }

    fun showEmptyList(show: Boolean) {
        showEmptyList.value = show
    }

    fun loadNextPage() {
        val lastPage = _findAllFoods.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllFoods()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllFoods()
        }
    }

    fun createFood(food: FoodRequestDTO) {
        viewModelScope.launch {
            repository.createNewFood(food = food)
                .onStart {
                    _createFood.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createFood.value = it
                }
        }
    }

    fun updateFood(food: UpdateFoodRequestDTO) {
        viewModelScope.launch {
            repository.updateFood(food = food)
                .onStart {
                    _updateFood.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updateFood.value = it
                }
        }
    }

    fun updatePriceFood(id: Long, price: UpdatePriceFoodRequestDTO) {
        viewModelScope.launch {
            repository.updatePriceFood(id = id, price = price)
                .onStart {
                    _updatePriceFood.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updatePriceFood.value = it
                }
        }
    }

    fun deleteFood(id: Long) {
        viewModelScope.launch {
            repository.deleteFood(id = id)
                .onStart {
                    _deleteFood.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteFood.value = it
                }
        }
    }

    fun resetFood(reset: ResetFood) {
        when (reset) {
            ResetFood.DELETE_FOOD -> {
                _deleteFood.value = ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetFood {
    DELETE_FOOD
}
