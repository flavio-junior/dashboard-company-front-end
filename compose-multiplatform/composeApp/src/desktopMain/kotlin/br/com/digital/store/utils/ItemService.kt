package br.com.digital.store.utils

import org.jetbrains.compose.resources.DrawableResource

data class ItemService(
    val icon: DrawableResource,
    val label: String,
    val route: String
)
