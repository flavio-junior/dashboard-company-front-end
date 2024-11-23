package br.com.digital.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.data.dto.SignInRequestDTO
import br.com.digital.store.utils.NetworkError
import br.com.digital.store.utils.Result
import br.com.digital.store.viewmodel.ApiViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

@Composable
@Preview
fun App() {
    val viewModel: ApiViewModel = getKoin().get()
    val signInResultState by viewModel.signInResult
    MaterialTheme {
        var censoredText by remember {
            mutableStateOf<String?>(null)
        }
        var uncensoredText by remember {
            mutableStateOf("")
        }
        var isLoading by remember {
            mutableStateOf(false)
        }
        var errorMessage by remember {
            mutableStateOf<NetworkError?>(null)
        }
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            LoadingButton(
                label = "Login",
                onClick = {
                    isLoading = true
                    errorMessage = null
                    viewModel.signIn(
                        SignInRequestDTO(
                            email = "john.doe@example.com",
                            password = "admin123"
                        )
                    )
                }
            )
            when (signInResultState) {
                is Result.Success -> {
                    val token = (signInResultState as Result.Success).data.accessToken
                    Description(description = token)
                }
                is Result.Error -> {
                    val error = (signInResultState as Result.Error).error
                    // Lide com o erro aqui
                }
                null -> {
                    // Estado inicial, sem resultado ainda
                }
            }
        }
    }
}