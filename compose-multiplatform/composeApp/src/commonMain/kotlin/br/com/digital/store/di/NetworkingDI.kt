package br.com.digital.store.di

import androidx.lifecycle.get
import br.com.digital.store.data.networking.remote.APIImpl
import br.com.digital.store.data.networking.remote.ApiRepository
import br.com.digital.store.viewmodel.ApiViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url {
                    protocol = io.ktor.http.URLProtocol.HTTP
                    host = "192.168.1.105"
                    port = 8001
                }
                contentType(ContentType.Application.Json)
            }
        }
    }
    single<ApiRepository> { APIImpl(get()) }
    singleOf(::ApiViewModel)
}