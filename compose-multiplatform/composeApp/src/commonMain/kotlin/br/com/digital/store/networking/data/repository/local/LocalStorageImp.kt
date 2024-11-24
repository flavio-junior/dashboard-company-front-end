package br.com.digital.store.networking.data.repository.local

import br.com.digital.store.networking.data.vo.TokenResponseVO

interface LocalStorageImp {
    suspend fun cleanToken()
    suspend fun getToken(): TokenResponseVO
    suspend fun saveToken(token: TokenResponseVO)
}
