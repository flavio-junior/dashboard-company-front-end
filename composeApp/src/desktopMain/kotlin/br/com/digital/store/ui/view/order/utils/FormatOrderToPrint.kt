package br.com.digital.store.ui.view.order.utils

import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.utils.alignLeftRight
import br.com.digital.store.features.order.utils.centerText
import br.com.digital.store.features.payment.domain.factory.paymentTypeFactoryLatin
import br.com.digital.store.features.payment.domain.factory.typeOrderFactoryResponse
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

fun formatOrderToPrint(
    order: OrderResponseVO
): String {
    val builder = StringBuilder()
    builder.append(centerText(text = "Comprovante de Pagamento")).append("\n")
    builder.append("\n")
    builder.append("Itens:\n\n")
    order.objects?.forEach { obj ->
        builder.append(
            alignLeftRight(
                left = "Nome:",
                right = obj.name
            )
        ).append("\n")
        builder.append(
            alignLeftRight(
                left = "Valor:",
                right = "$ ${String.format("%.2f", obj.price)}"
            )
        ).append("\n")
        builder.append(
            alignLeftRight(
                left = "Quantidade:",
                right = obj.quantity.toString()
            )
        ).append("\n")
        builder.append(
            alignLeftRight(
                left = "Valor Total:",
                right = "$ ${String.format("%.2f", obj.total)}"
            )
        ).append("\n\n")
    }

    builder.append(centerText(text = "Resumo:")).append("\n\n")

    builder.append(
        alignLeftRight(
            left = "Quantidade:",
            right = order.quantity.toString()
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Valor Total:",
            right = "$ ${String.format("%.2f", order.total)}"
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Reservas:",
            right = if (order.reservations?.isNotEmpty() == true) "Sim" else "Nao"
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "N. de Reservas:",
            right = "${order.reservations?.size ?: 0}"
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Tipo do Pedido:",
            right = typeOrderFactoryResponse(type = order.type)
        )
    ).append("\n\n")

    if(order.address?.id != null) {
        builder.append(centerText(text = "Endereco:")).append("\n\n")
        builder.append(
            alignLeftRight(
                left = "Rua:",
                right = order.address.street ?: EMPTY_TEXT
            )
        ).append("\n")
        builder.append(
            alignLeftRight(
                left = "Numero:",
                right = order.address.number.toString()
            )
        ).append("\n")
        builder.append(
            alignLeftRight(
                left = "Bairro:",
                right = order.address.district ?: EMPTY_TEXT
            )
        ).append("\n")
        builder.append(
            alignLeftRight(
                left = "Complemento:",
                right = order.address.complement ?: EMPTY_TEXT
            )
        ).append("\n\n")
    }

    builder.append(centerText(text = "Detalhes do Pagamento")).append("\n\n")

    builder.append(
        alignLeftRight(
            left = "Registro:",
            right = order.payment?.code.toString()
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Tipo:",
            right = paymentTypeFactoryLatin(payment = order.payment?.typePayment)
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Desconto:",
            right = if (order.payment?.discount == true) "Sim" else "Nao"
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Valor do Desconto:",
            right = "$ ${String.format("%.2f", order.payment?.valueDiscount ?: 0.0)}"
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Total do Pedido:",
            right = "$ ${String.format("%.2f", order.payment?.total)}"
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Data:",
            right = order.payment?.date ?: "N/A"
        )
    ).append("\n")
    builder.append(
        alignLeftRight(
            left = "Hora:",
            right = order.payment?.hour ?: "N/A"
        )
    ).append("\n\n\n")
    return builder.toString()
}
