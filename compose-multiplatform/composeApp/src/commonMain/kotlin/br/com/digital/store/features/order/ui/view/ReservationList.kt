package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.components.strings.StringsUtils.RESERVATION
import br.com.digital.store.components.ui.Title
import br.com.digital.store.theme.Themes

@Composable
fun ReservationList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxHeight()
    ) {
        Title(title = RESERVATION, textAlign = TextAlign.Center, modifier = modifier.fillMaxWidth())
    }
}
