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

    @Autowired
    private lateinit var userService: UserService

    @Transactional(readOnly = true)
    fun findAllProducts(
        user: User,
        name: String?,
        pageable: Pageable
    ): Page<ProductResponseVO> {
        val products: Page<Product>? =
            productRepository.findAllProducts(userId = user.id, name = name, pageable = pageable)
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
        user: User,
        productId: Long
    ): ProductResponseVO {
        val product = getProduct(userId = user.id, productId = productId)
        return parseObject(product, ProductResponseVO::class.java)
    }

    fun getProduct(
        userId: Long,
        productId: Long
    ): Product {
        val productSaved: Product? = productRepository.findProductById(userId = userId, productId = productId)
        if (productSaved != null) {
            return productSaved
        } else {
            throw ResourceNotFoundException(PRODUCT_NOT_FOUND)
        }
    }

    @Transactional
    fun createNewProduct(
        user: User,
        product: ProductRequestVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(userId = user.id, name = product.name)) {
            val userAuthenticated = userService.findUserById(id = user.id)
            val productResult: Product = parseObject(product, Product::class.java)
            productResult.categories =
                categoryService.converterCategories(userId = user.id, categories = product.categories)
            productResult.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            productResult.user = userAuthenticated
            return parseObject(productRepository.save(productResult), ProductResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_PRODUCT)
        }
    }

    private fun checkNameProductAlreadyExists(
        userId: Long,
        name: String
    ): Boolean {
        val productResult = productRepository.checkNameProductAlreadyExists(userId = userId, name = name)
        return productResult != null
    }

    @Transactional
    fun updateProduct(
        user: User,
        product: ProductResponseVO
    ): ProductResponseVO {
        if (!checkNameProductAlreadyExists(userId = user.id, name = product.name)) {
            val productSaved: Product = getProduct(userId = user.id, productId = product.id)
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
        user: User,
        productId: Long,
        price: PriceRequestVO
    ) {
        val productSaved = getProduct(userId = user.id, productId = productId)
        productRepository.updatePriceProduct(userId = user.id, idProduct = productSaved.id, price = price.price)
    }

    @Transactional
    fun restockProduct(
        user: User,
        productId: Long,
        restockProduct: RestockProductRequestVO
    ) {
        val productSaved = getProduct(userId = user.id, productId = productId)
        productRepository.restockProduct(userId = user.id, idProduct = productSaved.id, quantity = restockProduct.quantity)
    }

    @Transactional
    fun deleteProduct(
        user: User,
        productId: Long
    ) {
        val productSaved = getProduct(userId = user.id, productId = productId)
        productSaved.categories = null
        productRepository.deleteProductById(userId = user.id, productId = productId)
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "Product not found!"
        const val DUPLICATE_NAME_PRODUCT = "The product already exists"
    }
}
