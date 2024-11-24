package br.com.digital.store.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.networking.data.dto.SignInRequestDTO
import br.com.digital.store.networking.data.dto.TokenResponseDTO
import br.com.digital.store.networking.data.repository.local.DesktopLocalStorage
import br.com.digital.store.networking.data.repository.remote.ApiRepository
import br.com.digital.store.networking.data.vo.TokenResponseVO
import br.com.digital.store.networking.domain.converter.ConverterCommon
import br.com.digital.store.networking.utils.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ApiViewModel(
    private val localStorage: DesktopLocalStorage,
    private val repository: ApiRepository,
    private val converter: ConverterCommon
) : ViewModel() {

    private val _signIn =
        mutableStateOf<ObserveNetworkStateHandler<TokenResponseDTO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val signIn: State<ObserveNetworkStateHandler<TokenResponseDTO>> = _signIn

    private val _getTokenSaved =
        mutableStateOf<ObserveNetworkStateHandler<TokenResponseVO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val getTokenSaved: State<ObserveNetworkStateHandler<TokenResponseVO>> = _getTokenSaved

    fun signIn(signInRequestDTO: SignInRequestDTO) {
        viewModelScope.launch {
            repository.signIn(signIn = signInRequestDTO)
                .onStart {
                    _signIn.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _signIn.value = it
                    it.result?.let { result -> saveToken(token = result) }
                }
        }
    }

    fun saveToken(token: TokenResponseDTO) {
        viewModelScope.launch {
            localStorage.saveToken(
                converter.converterTokenRequestDTOToTokenResponseVO(
                    token
                )
            )
        }
    }

    fun getToken() {
        viewModelScope.launch {
            _getTokenSaved.value = ObserveNetworkStateHandler.Loading(l = true)
            val token = localStorage.getToken()
            _getTokenSaved.value = ObserveNetworkStateHandler.Success(s = token)
        }
    }

    fun cleanToken() {
        viewModelScope.launch {
            localStorage.cleanToken()
        }
    }

    fun resetStateSignIn() {
        _signIn.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
