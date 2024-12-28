package br.com.digital.store.features.category.domain

import br.com.digital.store.features.category.data.dto.CategoriesResponseDTO
import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import br.com.digital.store.features.category.data.vo.CategoriesResponseVO
import br.com.digital.store.features.category.data.vo.CategoryResponseVO
import br.com.digital.store.features.others.converterPageableDTOToVO

class ConverterCategory {

    fun converterContentDTOToVO(content: CategoriesResponseDTO): CategoriesResponseVO {
        return CategoriesResponseVO(
            totalPages = content.totalPages,
            content = converterCategoriesResponseDTOToVO(categories = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    fun converterCategoriesResponseDTOToVO(
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
