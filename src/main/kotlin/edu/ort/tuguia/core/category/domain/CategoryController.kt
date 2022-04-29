package edu.ort.tuguia.core.category.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@RequestBody @Valid category: Category): Category? {
        return this.categoryService.createCategory(category)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCategoryById(@PathVariable id: String): Category? {
        return this.categoryService.getCategoryById(id)
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllCategories(): List<Category> {
        return this.categoryService.getAllCategories()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCategory(@PathVariable id: String, @RequestBody @Valid category: Category): Category? {
        category.id = id
        return this.categoryService.updateCategory(category)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCategoryById(@PathVariable id: String): Category? {
        return this.categoryService.deleteCategoryById(id)
    }
}