package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.domain.factory.typeOrderFactory
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder

@Composable
fun CardObjectSelect(
    objectRequestDTO: ObjectRequestDTO
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {

                },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .height(height = Themes.size.spaceSize200)
            .width(width = Themes.size.spaceSize200)
    ) {
        SimpleText(
            text = typeOrderFactory(objectRequestDTO.type),
            color = Themes.colors.primary
        )
        SimpleText(text = objectRequestDTO.quantity.toString())
    }
}
