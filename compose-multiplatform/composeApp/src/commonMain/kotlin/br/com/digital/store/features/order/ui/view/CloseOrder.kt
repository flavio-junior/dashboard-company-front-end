package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CLOSE_ORDER
import br.com.digital.store.components.ui.LoadingButton

@Composable
fun CloseOrder(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(modifier = modifier) {
            LoadingButton(
                label = CLOSE_ORDER,
                onClick = {}
            )
        }
    }
}
