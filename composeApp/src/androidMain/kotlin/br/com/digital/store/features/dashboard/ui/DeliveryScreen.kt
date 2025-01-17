package br.com.digital.store.features.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.R
import br.com.digital.store.features.components.ActionButton
import br.com.digital.store.theme.Themes

@Composable
fun DeliveryScreen(
    goToBack: () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ActionButton(
            title = R.string.delivery,
            goToBack = goToBack
        )
    }
}
