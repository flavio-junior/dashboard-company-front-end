package br.com.digital.store.features.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.digital.store.theme.Themes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController = rememberNavController(),
    navGraph: NavHostController
) {
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .padding(all = Themes.size.spaceSize8)
            ) {
                BottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        BottomNavigationOrder(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            navGraph = navGraph
        )
    }
}
