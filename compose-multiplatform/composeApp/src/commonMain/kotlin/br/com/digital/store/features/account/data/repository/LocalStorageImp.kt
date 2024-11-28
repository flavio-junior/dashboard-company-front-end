package br.com.digital.store.features.account.data.repository

import br.com.digital.store.features.account.data.vo.TokenResponseVO

interface LocalStorageImp {
    suspend fun cleanToken()
    suspend fun getToken(): TokenResponseVO
    suspend fun saveToken(token: TokenResponseVO)
}
