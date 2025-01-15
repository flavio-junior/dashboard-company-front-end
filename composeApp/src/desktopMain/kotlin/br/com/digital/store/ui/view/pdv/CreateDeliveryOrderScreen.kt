package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.order.ui.GetAddressOrder

@Composable
fun CreateDeliveryOrderScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var observer: Boolean by remember { mutableStateOf(value = false) }
        var refresh: Boolean by remember { mutableStateOf(value = false) }
        GetAddressOrder(
            observer = observer,
            onRefresh = refresh,
            disabledRefresh = {
                refresh = false
            },
            addressResult = {
                AddItemsOrder(
                    address = it,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = {
                        refresh = true
                        onRefresh()
                    },
                    onError = {
                        observer = it
                    }
                )
            }
        )
    }
}
