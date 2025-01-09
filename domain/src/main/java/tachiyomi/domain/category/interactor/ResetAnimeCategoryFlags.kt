package tachiyomi.domain.category.interactor

import tachiyomi.domain.category.repository.AnimeCategoryRepository
import tachiyomi.domain.library.model.plus
import tachiyomi.domain.library.service.LibraryPreferences

class ResetAnimeCategoryFlags(
    private val preferences: LibraryPreferences,
    private val categoryRepository: AnimeCategoryRepository,
) {

    suspend fun await() {
        val sort = preferences.animeSortingMode().get()
        categoryRepository.updateAllAnimeCategoryFlags(sort.type + sort.direction)
    }
}
