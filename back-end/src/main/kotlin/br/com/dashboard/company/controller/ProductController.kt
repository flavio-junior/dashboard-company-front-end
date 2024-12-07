package br.com.dashboard.company.controller

import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.ForbiddenActionRequestException
import br.com.dashboard.company.service.ProductService
import br.com.dashboard.company.utils.common.PriceRequestVO
import br.com.dashboard.company.utils.others.ConstantsUtils.EMPTY_FIELDS
import br.com.dashboard.company.utils.others.MediaType.APPLICATION_JSON
import br.com.dashboard.company.vo.product.ProductRequestVO
import br.com.dashboard.company.vo.product.ProductResponseVO
import br.com.dashboard.company.vo.product.RestockProductRequestVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(value = ["/api/dashboard/company/products/v1"])
@Tag(name = "Product", description = "EndPoint For Managing All Products")
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @GetMapping(produces = [APPLICATION_JSON])
    @Operation(
        summary = "List All Products", description = "List All Products",
        tags = ["Product"], responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = ProductResponseVO::class)))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun findAllProducts(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false) name: String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "sort", defaultValue = "asc") sort: String
    ): ResponseEntity<Page<ProductResponseVO>> {
        val sortDirection: Sort.Direction =
            if ("desc".equals(sort, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable: Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"))
        return ResponseEntity.ok(
            productService.findAllProducts(user = user, name = name, pageable = pageable)
        )
    }

    @GetMapping(value = ["/find/product/by/{name}"], produces = [APPLICATION_JSON])
    @Operation(
        summary = "Find Product By Name With User Logged",
        description = "Find Product By Name With User Logged",
        tags = ["Product"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = ProductResponseVO::class)))
                ]
            ),
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun findProductByName(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "name") name: String,
    ): ResponseEntity<List<ProductResponseVO>> {
        return ResponseEntity.ok(
            productService.findProductByName(user = user, name = name)
        )
    }

    @GetMapping(
        value = ["/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Find Product By Id", description = "Find Product By Id",
        tags = ["Product"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(schema = Schema(implementation = ProductResponseVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun findProductById(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long
    ): ProductResponseVO {
        return productService.findProductById(user = user, productId = id)
    }

    @PostMapping(
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Create New Product", description = "Create New Product",
        tags = ["Product"],
        responses = [
            ApiResponse(
                description = "Created", responseCode = "201", content = [
                    Content(schema = Schema(implementation = ProductResponseVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Conflict", responseCode = "409", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun createNewProduct(
        @AuthenticationPrincipal user: User,
        @RequestBody product: ProductRequestVO
    ): ResponseEntity<ProductResponseVO> {
        require(
            value = product.name.isNotBlank() && product.name.isNotEmpty()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        val entity: ProductResponseVO = productService.createNewProduct(user = user, product = product)
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(entity.id).toUri()
        return ResponseEntity.created(uri).body(entity)
    }

    @PutMapping(
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Update Product", description = "Update Product",
        tags = ["Product"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(schema = Schema(implementation = ProductResponseVO::class))
                ]
            ),
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun updateProduct(
        @AuthenticationPrincipal user: User,
        @RequestBody product: ProductResponseVO
    ): ProductResponseVO {
        return productService.updateProduct(user = user, product = product)
    }

    @PatchMapping(
        value = ["update/price/product/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Update Price of Product", description = "Update Price of Product",
        tags = ["Product"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = PriceRequestVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Conflict", responseCode = "409", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun updatePriceProduct(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long,
        @RequestBody price: PriceRequestVO
    ): ResponseEntity<*> {
        productService.updatePriceProduct(user = user, productId = id, price = price)
        return ResponseEntity.noContent().build<Any>()
    }

    @PatchMapping(
        value = ["restock/product/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Restock Product", description = "Restock Product",
        tags = ["Product"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = RestockProductRequestVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun restockProduct(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long,
        @RequestBody restockProduct: RestockProductRequestVO
    ): ResponseEntity<*> {
        productService.restockProduct(user = user, productId = id, restockProduct = restockProduct)
        return ResponseEntity.noContent().build<Any>()
    }

    @DeleteMapping(
        value = ["/{id}"],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Delete Product By Id", description = "Delete Product By Id",
        tags = ["Product"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun deleteProduct(
        @AuthenticationPrincipal user: User,
        @PathVariable(value = "id") id: Long
    ): ResponseEntity<*> {
        productService.deleteProduct(user = user, productId = id)
        return ResponseEntity.noContent().build<Any>()
    }
}
