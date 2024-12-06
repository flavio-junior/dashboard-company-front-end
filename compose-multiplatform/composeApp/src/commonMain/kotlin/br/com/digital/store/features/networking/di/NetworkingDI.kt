package br.com.digital.store.features.networking.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(plugin = ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }
            install(plugin = Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(plugin = DefaultRequest) {
                url {
                    protocol = io.ktor.http.URLProtocol.HTTP
                    host = "192.168.1.106"
                    port = 8001
                }
                contentType(type = ContentType.Application.Json)
            }
        }
    }
}
