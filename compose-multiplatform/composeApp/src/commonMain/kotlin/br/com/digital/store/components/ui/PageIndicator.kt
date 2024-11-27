package br.com.digital.store.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.arrow_back
import br.com.digital.store.composeapp.generated.resources.arrow_forward
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.onBorder

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int = 0,
    totalPages: Int = 0,
    loadNextPage: () -> Unit = {},
    reloadPreviousPage: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
            .padding(
                top = Themes.size.spaceSize16,
                start = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val newValue = currentPage + NUMBER_ONE
        IconDefault(
            icon = Res.drawable.arrow_back,
            modifier = Modifier
                .onBorder(
                    onClick = reloadPreviousPage,
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = reloadPreviousPage
        )
        Title(
            title = "$newValue/$totalPages",
            modifier = Modifier.padding(horizontal = Themes.size.spaceSize16)
        )
        IconDefault(
            icon = Res.drawable.arrow_forward,
            modifier = Modifier
                .onBorder(
                    onClick = loadNextPage,
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = loadNextPage
        )
    }
}
