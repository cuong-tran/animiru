package tachiyomi.domain.history.interactor

import tachiyomi.domain.history.model.AnimeHistoryWithRelations
import tachiyomi.domain.history.repository.AnimeHistoryRepository

class RemoveAnimeHistory(
    private val repository: AnimeHistoryRepository,
) {

    suspend fun awaitAll(): Boolean {
        return repository.deleteAllAnimeHistory()
    }

    suspend fun await(history: AnimeHistoryWithRelations) {
        repository.resetAnimeHistory(history.id)
    }

    suspend fun await(animeId: Long) {
        repository.resetHistoryByAnimeId(animeId)
    }
}
