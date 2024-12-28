package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import br.com.digital.store.components.ui.Description
import br.com.digital.store.theme.Themes

@Composable
fun NumberTotalReservations(
    label: String,
    size: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        Description(description = label)
        Description(description = size.toString())
    }
}
