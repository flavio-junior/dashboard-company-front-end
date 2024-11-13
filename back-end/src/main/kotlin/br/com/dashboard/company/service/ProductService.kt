package br.com.dashboard.company.service

import br.com.dashboard.company.entities.category.Category
import br.com.dashboard.company.entities.product.Product
import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.ProductRepository
import br.com.dashboard.company.utils.ConverterUtils.parseObject
import br.com.dashboard.company.vo.product.ProductRequestVO
import br.com.dashboard.company.vo.product.ProductResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Transactional(readOnly = true)
    fun findAllProducts(): List<ProductResponseVO> {
        val products = productRepository.findAll()
        return products.map { product -> parseObject(product, ProductResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findProductById(
        id: Long
    ): ProductResponseVO {
        val product = getProduct(id = id)
        return parseObject(product, ProductResponseVO::class.java)
    }

    private fun getProduct(id: Long): Product {
        return productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(PRODUCT_NOT_FOUND) }
    }

    fun createNewProduct(
        product: ProductRequestVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(name = product.name)) {
            val productResult: Product = parseObject(product, Product::class.java)
            productResult.createdAt = Instant.now()
            product.categories?.forEach {
                val category = Category(id = it.id, name = it.name)
                productResult.categories?.add(element = category)
            }
            return parseObject(productRepository.save(productResult), ProductResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_PRODUCT)
        }
    }

    private fun checkNameProductAlreadyExists(
        name: String
    ): Boolean {
        val productResult = productRepository.checkNameProductAlreadyExists(name = name)
        return productResult != null
    }

    fun updateProduct(
        product: ProductResponseVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(name = product.name)) {
            val productSaved: Product = getProduct(id = product.id)
            productSaved.name = product.name
            productSaved.description = product.description
            productSaved.categories?.clear()
            product.categories?.forEach { category ->
                val newCategory = Category(id = category.id, name = category.name)
                productSaved.categories?.add(newCategory)
            }
            productSaved.price = product.price
            productSaved.quantity = product.quantity
            return parseObject(productRepository.save(productSaved), ProductResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_PRODUCT)
        }
    }

    fun deleteProduct(id: Long) {
        val product = getProduct(id = id)
        productRepository.delete(product)
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "Product not found!"
        const val DUPLICATE_NAME_PRODUCT = "The product already exists"
    }
}
