package br.com.digital.store.features.product.ui.view

import br.com.digital.store.features.product.utils.ProductUtils.CREATE_PRODUCT
import br.com.digital.store.features.product.utils.ProductUtils.DETAILS_PRODUCT
import br.com.digital.store.features.product.utils.ProductUtils.LIST_PRODUCTS
import br.com.digital.store.features.product.utils.ProductUtils.UPDATE_PRODUCT

enum class ItemsProduct(val text: String) {
    ListProducts(text = LIST_PRODUCTS),
    DetailsProduct(text = DETAILS_PRODUCT),
    UpdateProduct(text = UPDATE_PRODUCT),
    CreateProduct(text = CREATE_PRODUCT)
}
