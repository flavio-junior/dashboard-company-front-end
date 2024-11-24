package br.com.digital.store.networking.di

import br.com.digital.store.networking.data.repository.remote.ApiRemoteDataSource
import br.com.digital.store.networking.data.repository.remote.ApiRepository
import br.com.digital.store.networking.domain.converter.ConverterCommon
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
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
    single<ApiRepository> { ApiRemoteDataSource(get(), get()) }
    single { Dispatchers.IO }
    single { ConverterCommon() }
}
