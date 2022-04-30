package edu.ort.tuguia.core.category.domain

import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

interface CategoryService {
    fun createCategory(category: Category): Category
    fun getCategoryById(id: String): Category
    fun getAllCategories(): List<Category>
    fun updateCategory(category: Category): Category
    fun deleteCategoryById(id: String): Category
}

@Service
class CategoryServiceImpl(private val categoryRepository: CategoryRepository): CategoryService {
    override fun createCategory(category: Category): Category {
        category.id = UUID.randomUUID().toString()
        category.createdAt = LocalDateTime.now()
        this.categoryRepository.saveCategory(category)

        return category
    }

    override fun getCategoryById(id: String): Category {
        return this.categoryRepository.getCategoryById(id)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "La categoría con id $id no existe")
    }

    override fun getAllCategories(): List<Category> {
        return this.categoryRepository.getAllCategories()
    }

    override fun updateCategory(category: Category): Category {
        val queryCategory = this.getCategoryById(category.id)

        queryCategory.name = category.name
        queryCategory.description = category.description
        queryCategory.updatedAt = LocalDateTime.now()

        this.categoryRepository.saveCategory(queryCategory)

        return queryCategory
    }

    override fun deleteCategoryById(id: String): Category {
        val category = this.getCategoryById(id)

        this.categoryRepository.deleteCategory(category)

        return category
    }
}