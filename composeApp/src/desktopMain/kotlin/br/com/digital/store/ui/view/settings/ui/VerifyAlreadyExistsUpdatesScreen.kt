package br.com.digital.store.ui.view.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.Version
import br.com.digital.store.components.strings.StringsUtils.AVAILABLE_UPDATES
import br.com.digital.store.components.strings.StringsUtils.DOWNLOADING_UPDATES
import br.com.digital.store.components.strings.StringsUtils.DOWNLOAD_UPDATES
import br.com.digital.store.components.strings.StringsUtils.OUTDATED_SYSTEM
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.UPDATED_SYSTEM
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IconDefault
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
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.onBorder
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.contentLength
import io.ktor.utils.io.core.remaining
import io.ktor.utils.io.readRemaining
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.readByteArray
import org.koin.mp.KoinPlatform.getKoin
import java.awt.Desktop
import java.io.File

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
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<VersionResponseDTO> by remember { viewModel.checkUpdates }
    var linkToDownload: String? by remember { mutableStateOf(value = EMPTY_TEXT) }
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
                    linkToDownload = it.result.url ?: EMPTY_TEXT
                }
            }
        }
    )
    DownloadSystemUpdate(url = linkToDownload)
}

@Composable
fun DownloadSystemUpdate(
    url: String? = EMPTY_TEXT
) {
    var progress by remember { mutableStateOf(value = 0f) }
    var isDownloading by remember { mutableStateOf(value = false) }
    val scope = rememberCoroutineScope()
    val download = {
        scope.launch {
            isDownloading = true
            val file = downloadFile(url = url ?: EMPTY_TEXT) { downloaded, total ->
                progress = downloaded / total.toFloat()
            }
            isDownloading = false
            file?.let { executeInstaller(it) }
        }
    }
    if (isDownloading) {
        LoadingData(label = DOWNLOADING_UPDATES)
        LinearProgressIndicator(progress = progress)
    } else {
        if (url?.isNotEmpty() == true) {
            ClickDownloadUpdate(
                onClick = { download() }
            )
        }
    }
}

@Composable
private fun ClickDownloadUpdate(
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .onBorder(
                onClick = onClick,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .padding(all = Themes.size.spaceSize16)
    ) {
        IconDefault(
            icon = Res.drawable.cloud_download,
            size = Themes.size.spaceSize48
        )
        Title(title = DOWNLOAD_UPDATES)
    }
}

private suspend fun downloadFile(
    url: String,
    onProgress: (Long, Long) -> Unit
): File? {
    val client = HttpClient()
    val tempFile = withContext(Dispatchers.IO) {
        File.createTempFile("update", ".exe")
    }
    return try {
        client.get(url).let { httpResponse ->
            val totalBytes = httpResponse.contentLength() ?: 1L
            var receivedBytes = 0L
            val byteStream = tempFile.outputStream().buffered()

            httpResponse.bodyAsChannel().apply {
                while (!isClosedForRead) {
                    val chunk = readRemaining(max = 1024)
                    byteStream.write(chunk.readByteArray())
                    receivedBytes += chunk.remaining
                    onProgress(receivedBytes, totalBytes)
                }
            }
            byteStream.close()
            tempFile
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        client.close()
    }
}

private fun executeInstaller(
    file: File
) {
    try {
        Desktop.getDesktop().open(file)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
