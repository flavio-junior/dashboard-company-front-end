package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder

@Composable
fun CashRegisterScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .onBorder(
                onClick = { },
                width = Themes.size.spaceSize2,
                spaceSize = Themes.size.spaceSize8,
                color = Themes.colors.primary
            )
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {

    }
}