package br.com.digital.store.features.account.data.repository.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.digital.store.features.account.data.vo.TokenResponseVO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalStorage(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun cleanToken() {
        dataStore.edit { settings ->
            settings.remove(PREFERENCES_USER)
            settings.remove(PREFERENCES_AUTHENTICATED)
            settings.remove(PREFERENCES_CREATED)
            settings.remove(PREFERENCES_TYPE)
            settings.remove(PREFERENCES_EXPIRATION)
            settings.remove(PREFERENCES_ACCESS_TOKEN)
            settings.remove(PREFERENCES_REFRESH_TOKEN)
        }
    }

    suspend fun getToken(): TokenResponseVO {
        return dataStore.data.map { currentPreferences ->
            TokenResponseVO(
                user = currentPreferences[PREFERENCES_USER] ?: "",
                authenticated = currentPreferences[PREFERENCES_AUTHENTICATED] ?: false,
                created = currentPreferences[PREFERENCES_CREATED] ?: "",
                type = currentPreferences[PREFERENCES_TYPE] ?: "",
                expiration = currentPreferences[PREFERENCES_EXPIRATION] ?: "",
                accessToken = currentPreferences[PREFERENCES_ACCESS_TOKEN] ?: "",
                refreshToken = currentPreferences[PREFERENCES_REFRESH_TOKEN] ?: ""
            )
        }.first()
    }

    suspend fun saveToken(token: TokenResponseVO) {
        dataStore.edit { settings ->
            settings[PREFERENCES_USER] = token.user
            settings[PREFERENCES_AUTHENTICATED] = token.authenticated
            settings[PREFERENCES_CREATED] = token.created
            settings[PREFERENCES_TYPE] = token.type
            settings[PREFERENCES_EXPIRATION] = token.expiration
            settings[PREFERENCES_ACCESS_TOKEN] = token.accessToken
            settings[PREFERENCES_REFRESH_TOKEN] = token.refreshToken
        }
    }

    companion object {
        private val PREFERENCES_USER = stringPreferencesKey(name = "user")
        private val PREFERENCES_AUTHENTICATED = booleanPreferencesKey(name = "authenticated")
        private val PREFERENCES_CREATED = stringPreferencesKey(name = "created")
        private val PREFERENCES_TYPE = stringPreferencesKey(name = "type_account")
        private val PREFERENCES_EXPIRATION = stringPreferencesKey(name = "expiration")
        private val PREFERENCES_ACCESS_TOKEN = stringPreferencesKey(name = "access_token")
        private val PREFERENCES_REFRESH_TOKEN = stringPreferencesKey(name = "refresh_token")
    }
}
