package br.com.digital.store.features.product.utils

object ProductUtils {
    const val PRODUCTS = "Produtos"
    const val LIST_PRODUCTS = "Lista de Produtos"
    const val DETAILS_PRODUCT = "Detalhes do Produto"
    const val UPDATE_PRODUCT = "Atualizar Produto:"
    const val UPDATE_PRICE_PRODUCT = "Atualizar preço do produto"
    const val RESTOCK_PRODUCT = "Repor Estoque do Produto"
    const val CREATE_PRODUCT = "Criar Novo Produto"
    const val NAME_PRODUCT = "Nome do Produto"
    const val NEW_NAME_PRODUCT = "Novo nome do Produto"
    const val DELETE_PRODUCT = "Apagar Produto"
    const val ADD_PRODUCTS = "Adicionar produtos"
    const val NO_PRODUCTS_SELECTED = "Nenhum produto selecionado"
    const val EMPTY_LIST_PRODUCTS = "Nenhum Produto Encontrado!"
}

fun checkBodyProductIsNull(
    name: String,
    price: Double,
    quantity: Int
): Boolean {
    return name.isEmpty() && price == 0.0 && quantity == 0
}
