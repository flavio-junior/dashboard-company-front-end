package br.com.digital.store.features.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.digital.store.R
import br.com.digital.store.features.components.OptionButton
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_DELIVERY_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_RESERVATION
import br.com.digital.store.navigation.RouteApp
import br.com.digital.store.theme.Themes

@Composable
fun PdvScreen(
    navGraph: NavHostController
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize36)
            .wrapContentSize(align = Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        OptionButton(
            icon = R.drawable.delivery_truck,
            label = CREATE_DELIVERY_ORDER,
            onClick = { navGraph.navigate(route = RouteApp.Delivery.item) }
        )
        OptionButton(
            icon = R.drawable.order,
            label = CREATE_ORDER,
            onClick = { navGraph.navigate(route = RouteApp.Order.item) }
        )
        OptionButton(
            icon = R.drawable.reservation,
            label = CREATE_RESERVATION,
            onClick = { navGraph.navigate(route = RouteApp.Reservation.item) }
        )
    }
}
