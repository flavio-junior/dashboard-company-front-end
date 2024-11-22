package br.com.digital.store.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    var enabled: Boolean by remember { mutableStateOf(value = false) }
    enabled = isEnabled
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .onBorder(
                onClick = onClick,
                spaceSize = Themes.size.spaceSize36,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(color = Themes.colors.secondary)
            .padding(all = Themes.size.spaceSize18)
            .fillMaxWidth()
    ) {
        if (enabled) {
            CircularProgressIndicator()
        } else {
            Description(description = label.lowercase())
        }
    }
}

