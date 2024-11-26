package br.com.digital.store.components.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.downward
import br.com.digital.store.composeapp.generated.resources.upward
import br.com.digital.store.strings.StringsUtils.ASC
import br.com.digital.store.strings.StringsUtils.DESC
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder

@Composable
fun SortBy(
    onClick: (String) -> Unit = {}
) {
    var sortBy: Boolean by remember { mutableStateOf(value = false) }
    IconDefault(
        icon = if (sortBy) Res.drawable.upward else Res.drawable.downward,
        modifier = Modifier
            .onBorder(
                onClick = {},
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize1
            )
            .size(size = Themes.size.spaceSize64)
            .padding(all = Themes.size.spaceSize8),
        onClick = {
            sortBy = !sortBy
            onClick(if (sortBy) DESC else ASC)
        }
    )
}

