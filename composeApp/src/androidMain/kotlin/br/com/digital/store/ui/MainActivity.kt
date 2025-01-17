package br.com.digital.store.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.digital.store.features.components.SetStatusBarColor
import br.com.digital.store.navigation.AndroidNavigation
import br.com.digital.store.theme.Themes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetStatusBarColor(color = Themes.colors.secondary)
            AndroidNavigation()
        }
    }
}
