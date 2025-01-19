package br.com.digital.store.ui.view.order.utils

import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.status.ObjectStatus
import br.com.digital.store.features.order.utils.alignLeftRight
import br.com.digital.store.features.order.utils.centerText
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

fun formatItemsToPrint(
    order: OrderResponseVO
): String {
    val builder = StringBuilder()
    builder.append("\n")
    builder.append(centerText(text = "Pedido Pendente")).append("\n")
    builder.append("\n")
    builder.append("Itens:\n\n")

    order.objects?.filter { it.status == ObjectStatus.PENDING }?.forEach { obj ->
        obj.overview?.filter { it.status == ObjectStatus.PENDING }?.forEach { overview ->
            builder.append(
                alignLeftRight(
                    left = "Nome:",
                    right = obj.name
                )
            ).append("\n")
            builder.append(
                alignLeftRight(
                    left = "Quantidade:",
                    right = overview.quantity.toString()
                )
            ).append("\n")
            builder.append(
                alignLeftRight(
                    left = "Data:",
                    right = overview.createdAt ?: EMPTY_TEXT
                )
            ).append("\n\n")
        }
    }

    builder.append(centerText(text = "Detalhes do Pedido")).append("\n\n")

    builder.append(
        alignLeftRight(
            left = "Registro:",
            right = order.id.toString()
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Data:",
            right = order.createdAt
        )
    ).append("\n\n")
    builder.append("\n\n")
    return builder.toString()
}
