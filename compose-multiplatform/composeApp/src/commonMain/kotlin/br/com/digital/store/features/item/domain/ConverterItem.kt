package br.com.digital.store.features.item.domain

import br.com.digital.store.common.item.dto.ItemResponseDTO
import br.com.digital.store.common.item.dto.ItemsResponseDTO
import br.com.digital.store.common.item.vo.ItemResponseVO
import br.com.digital.store.common.item.vo.ItemsResponseVO
import br.com.digital.store.common.others.converterPageableDTOToVO

class ConverterItem {

    fun converterContentDTOToVO(content: ItemsResponseDTO): ItemsResponseVO {
        return ItemsResponseVO(
            totalPages = content.totalPages,
            content = converterItemsResponseDTOToVO(items = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterItemsResponseDTOToVO(
        items: List<ItemResponseDTO>
    ): List<ItemResponseVO> {
        return items.map {
            ItemResponseVO(
                id = it.id,
                name = it.name,
                price = it.price
            )
        }
    }
}
