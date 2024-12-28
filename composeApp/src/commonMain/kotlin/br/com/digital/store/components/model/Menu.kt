package br.com.digital.store.components.model

import org.jetbrains.compose.resources.DrawableResource

data class Menu(
    val icon: DrawableResource,
    val label: String,
    val route: String
)
