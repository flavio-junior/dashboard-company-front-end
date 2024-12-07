package br.com.dashboard.company.service

import br.com.dashboard.company.entities.product.Product
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.ProductRepository
import br.com.dashboard.company.utils.common.PriceRequestVO
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.product.ProductRequestVO
import br.com.dashboard.company.vo.product.ProductResponseVO
import br.com.dashboard.company.vo.product.RestockProductRequestVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var categoryService: CategoryService

    @Transactional(readOnly = true)
    fun findAllProducts(
        name: String?,
        pageable: Pageable
    ): Page<ProductResponseVO> {
        val products: Page<Product>? = productRepository.findAllProducts(name = name, pageable = pageable)
        return products?.map { product -> parseObject(product, ProductResponseVO::class.java) }
            ?: throw ResourceNotFoundException(message = PRODUCT_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun findProductByName(
        user: User,
        name: String
    ): List<ProductResponseVO> {
        val products: List<Product> = productRepository.findProductByName(userId = user.id, name = name)
        return products.map { product -> parseObject(product, ProductResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findProductById(
        id: Long
    ): ProductResponseVO {
        val product = getProduct(id = id)
        return parseObject(product, ProductResponseVO::class.java)
    }

    fun getProduct(
        id: Long
    ): Product {
        return productRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(PRODUCT_NOT_FOUND) }
    }

    @Transactional
    fun createNewProduct(
        user: User,
        product: ProductRequestVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(name = product.name)) {
            val productResult: Product = parseObject(product, Product::class.java)
            productResult.categories =
                categoryService.converterCategories(userId = user.id, categories = product.categories)
            productResult.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
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

    @Transactional
    fun updateProduct(
        user: User,
        product: ProductResponseVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(name = product.name)) {
            val productSaved: Product = getProduct(id = product.id)
            productSaved.name = product.name
            productSaved.categories?.clear()
            productSaved.categories =
                categoryService.converterCategories(userId = user.id, categories = product.categories)
            productSaved.price = product.price
            productSaved.quantity = product.quantity
            return parseObject(productRepository.save(productSaved), ProductResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_PRODUCT)
        }
    }

    @Transactional
    fun updatePriceProduct(
        idProduct: Long,
        price: PriceRequestVO
    ) {
        getProduct(id = idProduct)
        productRepository.updatePriceProduct(idProduct = idProduct, price = price.price)
    }

    @Transactional
    fun restockProduct(
        idProduct: Long,
        restockProduct: RestockProductRequestVO
    ) {
        getProduct(id = idProduct)
        productRepository.restockProduct(idProduct = idProduct, quantity = restockProduct.quantity)
    }

    fun deleteProduct(id: Long) {
        val product = getProduct(id = id)
        product.categories = null
        productRepository.delete(product)
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "Product not found!"
        const val DUPLICATE_NAME_PRODUCT = "The product already exists"
    }
}
