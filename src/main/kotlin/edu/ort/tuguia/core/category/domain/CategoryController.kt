package edu.ort.tuguia.core.category.domain

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Categories")
@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {
    @Operation(summary = "Create a category")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@RequestBody @Valid @Parameter(description = "Category") category: Category): Category? {
        return this.categoryService.createCategory(category)
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCategoryById(@PathVariable @Parameter(description = "ID of category") id: String): Category? {
        return this.categoryService.getCategoryById(id)
    }

    @Operation(summary = "Get category by name")
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    fun getCategoryByName(@PathVariable @Parameter(description = "Name of category") name: String): Category? {
        return this.categoryService.getCategoryByName(name)
    }

    @Operation(summary = "Get all categories")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllCategories(): List<Category> {
        return this.categoryService.getAllCategories()
    }

    @Operation(summary = "Update a category")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCategory(
        @PathVariable @Parameter(description = "ID of category") id: String,
        @RequestBody @Valid @Parameter(description = "Category") category: Category
    ): Category? {
        category.id = id
        return this.categoryService.updateCategory(category)
    }

    @Operation(summary = "Delete category by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCategoryById(@PathVariable @Parameter(description = "ID of category") id: String): Category? {
        return this.categoryService.deleteCategoryById(id)
    }
}