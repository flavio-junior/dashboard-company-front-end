package br.com.digital.store.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder

@Composable
fun <T> Tag(
    text: String,
    value: T,
    enabled: Boolean = false,
    onCheck: (Boolean) -> Unit = {},
    onResult: @Composable (T) -> Unit = {}
) {
    onCheck(false)
    var isChecked by remember { mutableStateOf(value = enabled) }
    var selected by remember { mutableStateOf(value = enabled) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    isChecked = !isChecked
                    selected = !selected
                },
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(if (isChecked && enabled) ITEM_SELECTED else Themes.colors.secondary)
            .padding(all = Themes.size.spaceSize14)
    ) {
        Description(
            description = text,
            color = if (selected) Themes.colors.background else ITEM_SELECTED
        )
    }
    if (isChecked) {
        onCheck(true)
        onResult(value)
    } else {
        onCheck(false)
        onResult(value)
    }
}
