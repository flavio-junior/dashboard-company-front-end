package br.com.digital.store.features.product.data.repository

import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.product.data.dto.ProductRequestDTO
import br.com.digital.store.features.product.data.dto.ProductResponseDTO
import br.com.digital.store.features.product.data.dto.ProductsResponseDTO
import br.com.digital.store.features.product.data.dto.RestockProductRequestDTO
import br.com.digital.store.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.digital.store.features.product.data.dto.UpdateProductRequestDTO
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun findAllProducts(
        name: String = EMPTY_TEXT,
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ProductsResponseDTO>>
    fun finProductByName(name: String): Flow<ObserveNetworkStateHandler<List<ProductResponseDTO>>>
    fun createNewProduct(product: ProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updateProduct(product: UpdateProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updatePriceProduct(id: Long, price: UpdatePriceProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun restockProduct(id: Long, stock: RestockProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteProduct(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
