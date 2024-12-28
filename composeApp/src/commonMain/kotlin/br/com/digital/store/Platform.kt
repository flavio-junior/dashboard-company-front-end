package br.com.digital.store

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform