package br.com.digital.store.features.others

import br.com.digital.store.features.others.dto.PageableDTO
import br.com.digital.store.features.others.vo.PageableVO

fun converterPageableDTOToVO(pageable: PageableDTO): PageableVO {
    return PageableVO(
        pageNumber = pageable.pageNumber
    )
}
