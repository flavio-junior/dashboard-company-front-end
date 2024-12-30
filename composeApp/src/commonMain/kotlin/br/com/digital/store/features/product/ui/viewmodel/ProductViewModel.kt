package br.com.digital.store.features.product.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.product.data.dto.ProductRequestDTO
import br.com.digital.store.features.product.data.dto.ProductResponseDTO
import br.com.digital.store.features.product.data.dto.RestockProductRequestDTO
import br.com.digital.store.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.digital.store.features.product.data.dto.UpdateProductRequestDTO
import br.com.digital.store.features.product.data.repository.ProductRepository
import br.com.digital.store.features.product.data.vo.ProductsResponseVO
import br.com.digital.store.features.product.domain.ConverterProduct
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository,
    private val converter: ConverterProduct
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllProducts =
        mutableStateOf<ObserveNetworkStateHandler<ProductsResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllProducts: State<ObserveNetworkStateHandler<ProductsResponseVO>> =
        _findAllProducts

    var showEmptyList = mutableStateOf(value = true)

    private val _findProductByName =
        mutableStateOf<ObserveNetworkStateHandler<List<ProductResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findProductByName: State<ObserveNetworkStateHandler<List<ProductResponseDTO>>> =
        _findProductByName

    private val _createProduct =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createProduct: State<ObserveNetworkStateHandler<Unit>> = _createProduct

    private val _updateProduct =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateProduct: State<ObserveNetworkStateHandler<Unit>> = _updateProduct

    private val _updatePriceProduct =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updatePriceProduct: State<ObserveNetworkStateHandler<Unit>> = _updatePriceProduct

    private val _restockProduct =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val restockProduct: State<ObserveNetworkStateHandler<Unit>> = _restockProduct

    private val _deleteProduct =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteProduct: State<ObserveNetworkStateHandler<Unit>> = _deleteProduct

    fun findAllProducts(
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
            repository.findAllProducts(
                name = name,
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllProducts.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        val objectConverted = converter.converterContentDTOToVO(content = response)
                        if (objectConverted.content.isNotEmpty()) {
                            showEmptyList.value = false
                            _findAllProducts.value = ObserveNetworkStateHandler.Success(
                                s = objectConverted
                            )
                        } else {
                            _findAllProducts.value = ObserveNetworkStateHandler.Success(
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
        val lastPage = _findAllProducts.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllProducts()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllProducts()
        }
    }

    fun findProductByName(name: String) {
        viewModelScope.launch {
            repository.finProductByName(name = name)
                .onStart {
                    _findProductByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findProductByName.value = it
                }
        }
    }

    fun createProduct(product: ProductRequestDTO) {
        viewModelScope.launch {
            repository.createNewProduct(product = product)
                .onStart {
                    _createProduct.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createProduct.value = it
                }
        }
    }

    fun updateProduct(product: UpdateProductRequestDTO) {
        viewModelScope.launch {
            repository.updateProduct(product = product)
                .onStart {
                    _updateProduct.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updateProduct.value = it
                }
        }
    }

    fun updatePriceProduct(id: Long, price: UpdatePriceProductRequestDTO) {
        viewModelScope.launch {
            repository.updatePriceProduct(id = id, price = price)
                .onStart {
                    _updatePriceProduct.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updatePriceProduct.value = it
                }
        }
    }

    fun restockProduct(id: Long, stock: RestockProductRequestDTO) {
        viewModelScope.launch {
            repository.restockProduct(id = id, stock = stock)
                .onStart {
                    _restockProduct.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _restockProduct.value = it
                }
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            repository.deleteProduct(id = id)
                .onStart {
                    _deleteProduct.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteProduct.value = it
                }
        }
    }

    fun resetProduct(reset: ResetProduct) {
        when (reset) {
            ResetProduct.FIND_BY_NAME -> {
                _findProductByName.value = ObserveNetworkStateHandler.Loading(l = false)
            }

            ResetProduct.CREATE_PRODUCT -> {
                _createProduct.value = ObserveNetworkStateHandler.Loading(l = false)
            }
        }
    }
}

enum class ResetProduct {
    FIND_BY_NAME,
    CREATE_PRODUCT
}
