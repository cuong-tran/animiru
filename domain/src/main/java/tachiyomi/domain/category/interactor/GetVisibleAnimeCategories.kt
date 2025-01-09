package tachiyomi.domain.category.interactor

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.category.model.Category
import tachiyomi.domain.category.repository.AnimeCategoryRepository

class GetVisibleAnimeCategories(
    private val categoryRepository: AnimeCategoryRepository,
) {
    fun subscribe(): Flow<List<Category>> {
        return categoryRepository.getAllVisibleAnimeCategoriesAsFlow()
    }

    fun subscribe(animeId: Long): Flow<List<Category>> {
        return categoryRepository.getVisibleCategoriesByAnimeIdAsFlow(animeId)
    }

    suspend fun await(): List<Category> {
        return categoryRepository.getAllVisibleAnimeCategories()
    }

    suspend fun await(animeId: Long): List<Category> {
        return categoryRepository.getVisibleCategoriesByAnimeId(animeId)
    }
}
