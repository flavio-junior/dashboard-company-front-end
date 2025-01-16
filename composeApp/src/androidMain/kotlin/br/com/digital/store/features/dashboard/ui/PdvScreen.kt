package br.com.digital.store.features.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.digital.store.R
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_DELIVERY_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_RESERVATION
import br.com.digital.store.theme.Themes
import br.com.digital.store.theme.TypeFont
import br.com.digital.store.utils.onBorder

@Composable
fun ActionsOrder(
    icon: Int,
    label: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = Modifier
            .onBorder(
                onClick = { },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Themes.colors.primary
        )
        SimpleText(text = label, typeFont = TypeFont.ONEST_BOLD)
    }
}

@Composable
fun PdvScreen(
    navController: NavHostController = rememberNavController(),
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
        ActionsOrder(
            icon = R.drawable.delivery_truck,
            label = CREATE_DELIVERY_ORDER
        )
        ActionsOrder(
            icon = R.drawable.order,
            label = CREATE_ORDER
        )
        ActionsOrder(
            icon = R.drawable.reservation,
            label = CREATE_RESERVATION
        )
    }
}
