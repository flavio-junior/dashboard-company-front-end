package br.com.dashboard.company.controller

import br.com.dashboard.company.service.CategoryService
import br.com.dashboard.company.utils.MediaType.APPLICATION_JSON
import br.com.dashboard.company.vo.category.CategoryResponseVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/dashboard/company/categories/v1"])
@Tag(name = "Category", description = "EndPoint For Managing All Categories")
class CategoryController {

    @Autowired
    private lateinit var categoryService: CategoryService


    @GetMapping(produces = [APPLICATION_JSON])
    @Operation(
        summary = "List All Categories", description = "List All Categories",
        tags = ["Category"], responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(array = ArraySchema(schema = Schema(implementation = CategoryResponseVO::class)))
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
    fun findAllCategories(): ResponseEntity<List<CategoryResponseVO>> {
        return ResponseEntity.ok(
            categoryService.findAllCategories()
        )
    }
}
