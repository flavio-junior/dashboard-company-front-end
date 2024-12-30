package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.theme.Themes

@Composable
fun CreateReservationOrderScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.error)
            .fillMaxSize()
    ) {

    }
}
