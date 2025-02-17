package br.com.digital.store.ui.view.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.Version
import br.com.digital.store.components.model.BodyButton
import br.com.digital.store.components.model.TypeNavigation
import br.com.digital.store.components.strings.StringsUtils.AVAILABLE_UPDATES
import br.com.digital.store.components.strings.StringsUtils.DOWNLOAD_UPDATES
import br.com.digital.store.components.strings.StringsUtils.OUTDATED_SYSTEM
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.UPDATED_SYSTEM
import br.com.digital.store.components.ui.AlternativeButton
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Title
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.cloud_download
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.settings.data.dto.VersionResponseDTO
import br.com.digital.store.features.settings.ui.viewmodel.SettingsViewModel
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.settings.utils.openUrlInBrowser
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun VerifyAlreadyExistsUpdatesScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: SettingsViewModel = getKoin().get()
    LaunchedEffect(key1 = Unit) {
        viewModel.checkUpdates(version = Version.VERSION_NAME)
    }
    Column(
        modifier = Modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        ObserveNetworkStateHandlerVerifyAlreadyExistsUpdatesScreen(
            viewModel = viewModel,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerVerifyAlreadyExistsUpdatesScreen(
    viewModel: SettingsViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<VersionResponseDTO> by remember { viewModel.checkUpdates }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            if (it.result != null) {
                Title(title = AVAILABLE_UPDATES)
                if (it.result.status) {
                    Description(description = "$STATUS: $UPDATED_SYSTEM")
                } else {
                    Description(description = "$STATUS: $OUTDATED_SYSTEM")
                    AlternativeButton(
                        type = BodyButton(
                            icon = Res.drawable.cloud_download,
                            label = DOWNLOAD_UPDATES,
                            navigation = TypeNavigation.NAVIGATION,
                            count = 1
                        ),
                        goToNextScreen = {
                            openUrlInBrowser(url = it.result.url ?: EMPTY_TEXT)
                        }
                    )
                }
            }
        }
    )
}
