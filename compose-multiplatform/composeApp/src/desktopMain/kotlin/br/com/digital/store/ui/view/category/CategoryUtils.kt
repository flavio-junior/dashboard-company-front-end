package br.com.digital.store.ui.view.category

object CategoryUtils {
    const val CATEGORY_NAME = "Nome da categoria"
    const val NEW_NAME_CATEGORY = "Novo nome da categoria"
    const val SAVE_CATEGORY = "Salvar categoria"
    const val EDIT_CATEGORY = "Editar categoria"
    const val DELETE_CATEGORY = "Apagar categoria"
}

fun checkNameIsNull(categoryName: String): Boolean {
    return (categoryName.isEmpty())
}
