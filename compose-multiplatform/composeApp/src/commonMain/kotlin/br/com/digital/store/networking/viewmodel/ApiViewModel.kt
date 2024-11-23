package br.com.digital.store.networking.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.networking.data.dto.SignInRequestDTO
import br.com.digital.store.networking.data.dto.TokenResponseDTO
import br.com.digital.store.networking.data.repository.remote.ApiRepository
import br.com.digital.store.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.strings.StringsUtils.INVALID_EMAIL
import br.com.digital.store.strings.validateEmail
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ApiViewModel(
    private val repository: ApiRepository
) : ViewModel() {

    private val _signIn =
        mutableStateOf<ObserveNetworkStateHandler<TokenResponseDTO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val signIn: State<ObserveNetworkStateHandler<TokenResponseDTO>> = _signIn

    fun signIn(signInRequestDTO: SignInRequestDTO) {
        viewModelScope.launch {
            repository.signIn(signIn = signInRequestDTO)
                .onStart {
                    _signIn.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _signIn.value = it
                }
        }
    }
}
