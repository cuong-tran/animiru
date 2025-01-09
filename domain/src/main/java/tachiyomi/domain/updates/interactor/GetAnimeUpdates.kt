package tachiyomi.domain.updates.interactor

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.updates.model.AnimeUpdatesWithRelations
import tachiyomi.domain.updates.repository.AnimeUpdatesRepository
import java.time.Instant

class GetAnimeUpdates(
    private val repository: AnimeUpdatesRepository,
) {

    suspend fun await(seen: Boolean, after: Long): List<AnimeUpdatesWithRelations> {
        return repository.awaitWithSeen(seen, after, limit = 500)
    }

    fun subscribe(instant: Instant): Flow<List<AnimeUpdatesWithRelations>> {
        return repository.subscribeAllAnimeUpdates(instant.toEpochMilli(), limit = 500)
    }

    fun subscribe(seen: Boolean, after: Long): Flow<List<AnimeUpdatesWithRelations>> {
        return repository.subscribeWithSeen(seen, after, limit = 500)
    }
}
