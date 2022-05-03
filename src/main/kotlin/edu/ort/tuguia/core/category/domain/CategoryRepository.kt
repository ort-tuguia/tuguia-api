package edu.ort.tuguia.core.category.domain

interface CategoryRepository {
    fun saveCategory(category: Category)
    fun getCategoryById(id: String): Category?
    fun getCategoryByName(name: String): Category?
    fun getAllCategories(): List<Category>
    fun deleteCategory(category: Category)
}