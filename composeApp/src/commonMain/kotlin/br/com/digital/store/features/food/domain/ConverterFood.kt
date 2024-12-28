package br.com.digital.store.features.food.domain

import br.com.digital.store.features.category.domain.ConverterCategory
import br.com.digital.store.features.food.data.dto.FoodResponseDTO
import br.com.digital.store.features.food.data.dto.FoodsResponseDTO
import br.com.digital.store.features.food.data.vo.FoodResponseVO
import br.com.digital.store.features.food.data.vo.FoodsResponseVO
import br.com.digital.store.features.others.converterPageableDTOToVO

class ConverterFood(
    private val converter: ConverterCategory
) {

    fun converterContentDTOToVO(content: FoodsResponseDTO): FoodsResponseVO {
        return FoodsResponseVO(
            totalPages = content.totalPages,
            content = converterFoodsResponseDTOToVO(foods = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterFoodsResponseDTOToVO(
        foods: List<FoodResponseDTO>
    ): List<FoodResponseVO> {
        return foods.map {
            FoodResponseVO(
                id = it.id,
                name = it.name,
                categories = converter.converterCategoriesResponseDTOToVO(
                    categories = it.categories ?: emptyList()
                ),
                price = it.price
            )
        }
    }
}
