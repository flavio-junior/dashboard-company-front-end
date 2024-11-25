package br.com.digital.store.features.account.data

import br.com.digital.store.common.account.TokenResponseVO

interface LocalStorageImp {
    suspend fun cleanToken()
    suspend fun getToken(): TokenResponseVO
    suspend fun saveToken(token: TokenResponseVO)
}
