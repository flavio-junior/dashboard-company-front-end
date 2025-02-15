package br.com.digital.store.features.account.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.components.strings.StringsUtils.INVALID_EMAIL
import br.com.digital.store.components.strings.validateEmail
import br.com.digital.store.features.account.data.dto.SignInRequestDTO
import br.com.digital.store.features.account.data.dto.TokenResponseDTO
import br.com.digital.store.features.account.data.repository.AccountRepository
import br.com.digital.store.features.account.data.repository.local.LocalStorage
import br.com.digital.store.features.account.data.vo.TokenResponseVO
import br.com.digital.store.features.account.domain.converter.ConverterToken
import br.com.digital.store.features.networking.resources.DescriptionError
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AccountViewModel(
    private val localStorage: LocalStorage,
    private val repository: AccountRepository,
    private val converterToken: ConverterToken
) : ViewModel(), AccountViewModelImpl {

    private val _signIn =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val signIn: State<ObserveNetworkStateHandler<Unit>> = _signIn

    private val _getTokenSaved =
        mutableStateOf<ObserveNetworkStateHandler<TokenResponseVO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val getTokenSaved: State<ObserveNetworkStateHandler<TokenResponseVO>> = _getTokenSaved

    private val _refreshToken =
        mutableStateOf<ObserveNetworkStateHandler<TokenResponseVO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val refreshToken: State<ObserveNetworkStateHandler<TokenResponseVO>> = _refreshToken

    override fun signIn(signInRequestDTO: SignInRequestDTO) {
        if (validateEmail(email = signInRequestDTO.email)) {
            viewModelScope.launch {
                repository.signIn(signIn = signInRequestDTO)
                    .onStart {
                        _signIn.value = ObserveNetworkStateHandler.Loading(l = true)
                    }
                    .collect {
                        it.result?.let { result -> saveToken(token = result) }
                    }
            }
        } else {
            _signIn.value =
                ObserveNetworkStateHandler.Error(e = DescriptionError(message = INVALID_EMAIL))
        }
    }

    override fun getToken() {
        viewModelScope.launch {
            _getTokenSaved.value = ObserveNetworkStateHandler.Loading(l = true)
            val token = localStorage.getToken()
            _getTokenSaved.value = ObserveNetworkStateHandler.Success(s = token)
        }
    }

    override fun saveToken(token: TokenResponseDTO) {
        viewModelScope.launch {
            localStorage.saveToken(
                converterToken.converterTokenRequestDTOToTokenResponseVO(
                    token
                )
            )
        }
    }

    override fun refreshToken(email: String?, refresh: RefreshToken) {
        viewModelScope.launch {
            when (refresh) {
                RefreshToken.REFRESH_TOKEN -> {
                    if (email != null) {
                        repository.refreshToken(email)
                            .onStart {
                                _refreshToken.value = ObserveNetworkStateHandler.Loading(l = true)
                            }
                            .collect {
                                it.result?.let { result -> saveToken(token = result) }
                            }
                    } else {
                        localStorage.cleanToken()
                    }
                }

                RefreshToken.CLEAN_TOKEN -> {
                    localStorage.cleanToken()
                }
            }
        }
    }

    fun cleanToken() {
        viewModelScope.launch {
            localStorage.cleanToken()
        }
    }
}
