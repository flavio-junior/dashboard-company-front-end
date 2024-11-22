package br.com.digital.store.domain.factory

import br.com.digital.store.components.model.Menu
import lojavirtual.composeapp.generated.resources.Res
import lojavirtual.composeapp.generated.resources.mail

val menus = listOf(
    Menu(
        icon = Res.drawable.mail,
        label = "Categorias"
    ),
    Menu(
        icon = Res.drawable.mail,
        label = "Comidas"
    ),
    Menu(
        icon = Res.drawable.mail,
        label = "Produtos"
    ),
    Menu(
        icon = Res.drawable.mail,
        label = "Perdidos"
    ),
    Menu(
        icon = Res.drawable.mail,
        label = "Reservas"
    ),
    Menu(
        icon = Res.drawable.mail,
        label = "Items"
    ),
)