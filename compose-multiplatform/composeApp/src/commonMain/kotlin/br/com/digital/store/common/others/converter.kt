package br.com.digital.store.common.others

import br.com.digital.store.common.others.dto.PageableDTO
import br.com.digital.store.common.others.vo.PageableVO

fun converterPageableDTOToVO(pageable: PageableDTO): PageableVO {
    return PageableVO(
        pageNumber = pageable.pageNumber
    )
}
