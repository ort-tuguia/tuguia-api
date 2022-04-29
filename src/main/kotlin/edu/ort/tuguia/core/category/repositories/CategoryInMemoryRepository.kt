package edu.ort.tuguia.core.category.repositories

import edu.ort.tuguia.core.category.domain.Category
import edu.ort.tuguia.core.category.domain.CategoryRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("memdb")
class CategoryInMemoryRepository: CategoryRepository {
    var categories: HashMap<String, Category> = HashMap()

    override fun saveCategory(category: Category) {
        categories[category.id] = category
    }

    override fun getCategoryById(id: String): Category? {
        return categories[id]
    }

    override fun getAllCategories(): List<Category> {
        return ArrayList(categories.values)
    }

    override fun deleteCategory(category: Category) {
        categories.remove(category.id)
    }
}