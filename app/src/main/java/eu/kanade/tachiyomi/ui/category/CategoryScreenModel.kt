package eu.kanade.tachiyomi.ui.category

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tachiyomi.domain.category.interactor.CreateAnimeCategoryWithName
import tachiyomi.domain.category.interactor.DeleteAnimeCategory
import tachiyomi.domain.category.interactor.GetAnimeCategories
import tachiyomi.domain.category.interactor.GetVisibleAnimeCategories
import tachiyomi.domain.category.interactor.HideAnimeCategory
import tachiyomi.domain.category.interactor.RenameAnimeCategory
import tachiyomi.domain.category.interactor.ReorderAnimeCategory
import tachiyomi.domain.category.model.Category
import tachiyomi.domain.library.service.LibraryPreferences
import tachiyomi.i18n.MR
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class CategoryScreenModel(
    private val getAllCategories: GetAnimeCategories = Injekt.get(),
    private val getVisibleCategories: GetVisibleAnimeCategories = Injekt.get(),
    private val createCategoryWithName: CreateAnimeCategoryWithName = Injekt.get(),
    private val hideCategory: HideAnimeCategory = Injekt.get(),
    private val deleteCategory: DeleteAnimeCategory = Injekt.get(),
    private val reorderCategory: ReorderAnimeCategory = Injekt.get(),
    private val renameCategory: RenameAnimeCategory = Injekt.get(),
    private val libraryPreferences: LibraryPreferences = Injekt.get(),
) : StateScreenModel<CategoryScreenState>(CategoryScreenState.Loading) {

    private val _events: Channel<CategoryEvent> = Channel()
    val events = _events.receiveAsFlow()

    init {
        screenModelScope.launch {
            val allCategories = if (libraryPreferences.hideHiddenCategoriesSettings().get()) {
                getVisibleCategories.subscribe()
            } else {
                getAllCategories.subscribe()
            }

            allCategories.collectLatest { categories ->
                mutableState.update {
                    CategoryScreenState.Success(
                        categories = categories
                            .filterNot(Category::isSystemCategory)
                            .toImmutableList(),
                    )
                }
            }
        }
    }

    fun createCategory(name: String) {
        screenModelScope.launch {
            when (createCategoryWithName.await(name)) {
                is CreateAnimeCategoryWithName.Result.InternalError -> _events.send(
                    CategoryEvent.InternalError,
                )

                else -> {}
            }
        }
    }

    fun hideCategory(category: Category) {
        screenModelScope.launch {
            when (hideCategory.await(category)) {
                is HideAnimeCategory.Result.InternalError -> _events.send(
                    CategoryEvent.InternalError,
                )
                else -> {}
            }
        }
    }

    fun deleteCategory(categoryId: Long) {
        screenModelScope.launch {
            when (deleteCategory.await(categoryId = categoryId)) {
                is DeleteAnimeCategory.Result.InternalError -> _events.send(
                    CategoryEvent.InternalError,
                )
                else -> {}
            }
        }
    }

    fun sortAlphabetically() {
        screenModelScope.launch {
            when (reorderCategory.sortAlphabetically()) {
                is ReorderAnimeCategory.Result.InternalError -> _events.send(CategoryEvent.InternalError)
                else -> {}
            }
        }
    }

    fun moveUp(category: Category) {
        screenModelScope.launch {
            when (reorderCategory.moveUp(category)) {
                is ReorderAnimeCategory.Result.InternalError -> _events.send(
                    CategoryEvent.InternalError,
                )
                else -> {}
            }
        }
    }

    fun moveDown(category: Category) {
        screenModelScope.launch {
            when (reorderCategory.moveDown(category)) {
                is ReorderAnimeCategory.Result.InternalError -> _events.send(
                    CategoryEvent.InternalError,
                )
                else -> {}
            }
        }
    }

    fun renameCategory(category: Category, name: String) {
        screenModelScope.launch {
            when (renameCategory.await(category, name)) {
                is RenameAnimeCategory.Result.InternalError -> _events.send(
                    CategoryEvent.InternalError,
                )
                else -> {}
            }
        }
    }

    fun showDialog(dialog: CategoryDialog) {
        mutableState.update {
            when (it) {
                CategoryScreenState.Loading -> it
                is CategoryScreenState.Success -> it.copy(dialog = dialog)
            }
        }
    }

    fun dismissDialog() {
        mutableState.update {
            when (it) {
                CategoryScreenState.Loading -> it
                is CategoryScreenState.Success -> it.copy(dialog = null)
            }
        }
    }
}

sealed interface CategoryDialog {
    data object Create : CategoryDialog
    data object SortAlphabetically : CategoryDialog
    data class Rename(val category: Category) : CategoryDialog
    data class Delete(val category: Category) : CategoryDialog
}

sealed interface CategoryEvent {
    sealed class LocalizedMessage(val stringRes: StringResource) : CategoryEvent
    data object InternalError : LocalizedMessage(MR.strings.internal_error)
}

sealed interface CategoryScreenState {

    @Immutable
    data object Loading : CategoryScreenState

    @Immutable
    data class Success(
        val categories: ImmutableList<Category>,
        val dialog: CategoryDialog? = null,
    ) : CategoryScreenState {

        val isEmpty: Boolean
            get() = categories.isEmpty()
    }
}
