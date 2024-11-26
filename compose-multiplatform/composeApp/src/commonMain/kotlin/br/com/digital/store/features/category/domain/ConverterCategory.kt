package br.com.digital.store.features.category.domain

import br.com.digital.store.common.category.dto.CategoriesResponseDTO
import br.com.digital.store.common.category.dto.CategoryResponseDTO
import br.com.digital.store.common.category.vo.CategoriesResponseVO
import br.com.digital.store.common.category.vo.CategoryResponseVO

class ConverterCategory {

    fun converterContentDTOToVO(content: CategoriesResponseDTO): CategoriesResponseVO {
        return CategoriesResponseVO(
            content = converterCategoriesResponseDTOToVO(categories = content.content)
        )
    }

    private fun converterCategoriesResponseDTOToVO(
        categories: List<CategoryResponseDTO>
    ): List<CategoryResponseVO> {
        return categories.map {
            CategoryResponseVO(
                id = it.id,
                name = it.name
            )
        }
    }
}
