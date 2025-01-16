package br.com.digital.store.features.dashboard.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.theme.Themes
import br.com.digital.store.theme.TypeFont
import br.com.digital.store.utils.onBorder

val itemsBottom = listOf(
    BottomNavigationRoute.PDV,
    BottomNavigationRoute.PendingOrders,
    BottomNavigationRoute.Settings
)

@Composable
fun BottomNavigation(
    navController: NavHostController
) {
    NavigationBar(
        containerColor = Themes.colors.background,
        modifier = Modifier
            .onBorder(
                onClick = { },
                spaceSize = Themes.size.spaceSize8,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
    ) {
        val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
        val currentRoute: String? = navBackStackEntry?.destination?.route
        itemsBottom.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.label),
                        tint = Themes.colors.primary
                    )
                },
                label = {
                    SimpleText(
                        text = stringResource(id = item.label),
                        typeFont = TypeFont.ONEST_BOLD
                    )
                },
                selected = currentRoute == item.route.name,
                onClick = {
                    navController.navigate(item.route.name) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Themes.colors.primary,
                    selectedTextColor = Themes.colors.primary,
                    indicatorColor = Themes.colors.secondary,
                    unselectedIconColor = Themes.colors.primary,
                    unselectedTextColor = Themes.colors.primary
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BottomNavigationPreview() {
    BottomNavigation(navController = rememberNavController())
}
