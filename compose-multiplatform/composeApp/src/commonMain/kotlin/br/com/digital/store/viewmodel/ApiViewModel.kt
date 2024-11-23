package br.com.digital.store.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digital.store.data.dto.SignInRequestDTO
import br.com.digital.store.data.dto.TokenResponseDTO
import br.com.digital.store.data.networking.remote.ApiRepository
import br.com.digital.store.utils.NetworkError
import br.com.digital.store.utils.Result
import kotlinx.coroutines.launch

class ApiViewModel(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val _signInResult = mutableStateOf<Result<TokenResponseDTO, NetworkError>?>(value = null)
    val signInResult: State<Result<TokenResponseDTO, NetworkError>?> = _signInResult

    fun signIn(signInRequestDTO: SignInRequestDTO) {
        viewModelScope.launch {
            _signInResult.value = apiRepository.signIn(signInRequestDTO)
        }
    }
}