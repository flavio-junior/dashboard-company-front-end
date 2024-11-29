package br.com.digital.store.features.product.di

import br.com.digital.store.features.category.domain.ConverterCategory
import br.com.digital.store.features.others.converterPageableDTOToVO
import br.com.digital.store.features.product.data.dto.ProductResponseDTO
import br.com.digital.store.features.product.data.dto.ProductsResponseDTO
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.features.product.data.vo.ProductsResponseVO

class ConverterProduct(
    private val converter: ConverterCategory
) {

    fun converterContentDTOToVO(content: ProductsResponseDTO): ProductsResponseVO {
        return ProductsResponseVO(
            totalPages = content.totalPages,
            content = converterProductsResponseDTOToVO(products = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterProductsResponseDTOToVO(
        products: List<ProductResponseDTO>
    ): List<ProductResponseVO> {
        return products.map {
            ProductResponseVO(
                id = it.id,
                name = it.name,
                categories = converter.converterCategoriesResponseDTOToVO(
                    categories = it.categories ?: emptyList()
                ),
                price = it.price,
                quantity = it.quantity
            )
        }
    }
}
