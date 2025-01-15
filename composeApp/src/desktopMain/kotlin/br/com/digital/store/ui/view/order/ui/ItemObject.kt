package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import br.com.digital.store.theme.Themes

@Composable
fun ItemObject(
    modifier: Modifier = Modifier,
    spaceBy: Dp = Themes.size.spaceSize16,
    body: @Composable () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = spaceBy),
        modifier = modifier
    ) {
        body()
    }
}