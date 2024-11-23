package br.com.digital.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.networking.data.dto.SignInRequestDTO
import br.com.digital.store.networking.utils.errorResult
import br.com.digital.store.networking.viewmodel.ApiViewModel
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

@Composable
@Preview
fun App() {
    val viewModel: ApiViewModel = getKoin().get()
    val state by viewModel.signIn
    MaterialTheme {
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            LoadingButton(
                label = "Login",
                onClick = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.signIn(
                        SignInRequestDTO(
                            email = "johns.doe@example.com",
                            password = "admin123"
                        )
                    )
                },
                isEnabled = observer.first
            )
            IsErrorMessage(isError = observer.second, message = observer.third)
            ObserveNetworkStateHandler(
                resultState = state,
                onError = {
                    errorResult(
                        description = state.exception,
                        message = {
                            observer = Triple(first = false, second = true, third = it)
                        },
                        openErrorScreen = { }
                    )
                },
                onSuccess = {
                    state.result?.let {
                        Description(description = it.accessToken)
                    }
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                }
            )
        }
    }
}