package br.com.digital.store.features.category.domain

import br.com.digital.store.common.category.dto.CategoriesResponseDTO
import br.com.digital.store.common.category.dto.CategoryResponseDTO
import br.com.digital.store.common.category.vo.CategoriesResponseVO
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.common.others.dto.PageableDTO
import br.com.digital.store.common.others.vo.PageableVO

class ConverterCategory {

    fun converterContentDTOToVO(content: CategoriesResponseDTO): CategoriesResponseVO {
        return CategoriesResponseVO(
            totalPages = content.totalPages,
            content = converterCategoriesResponseDTOToVO(categories = content.content),
            converterPageableDTOToVO(pageable = content.pageable)
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

    private fun converterPageableDTOToVO(pageable: PageableDTO): PageableVO {
        return PageableVO(
            pageNumber = pageable.pageNumber
        )
    }
}
