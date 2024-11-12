package br.com.dashboard.company.service

import br.com.dashboard.company.repository.ProductRepository
import br.com.dashboard.company.utils.ConverterUtils.parseObject
import br.com.dashboard.company.vo.product.ProductResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Transactional(readOnly = true)
    fun findAllProducts(): List<ProductResponseVO> {
        val products = productRepository.findAll()
        return products.map { product -> parseObject(product, ProductResponseVO::class.java) }
    }
}
