package tachiyomi.domain.source.interactor

import kotlinx.coroutines.flow.Flow
import tachiyomi.domain.source.model.AnimeSourceWithCount
import tachiyomi.domain.source.repository.AnimeSourceRepository

class GetAnimeSourcesWithNonLibraryAnime(
    private val repository: AnimeSourceRepository,
) {

    fun subscribe(): Flow<List<AnimeSourceWithCount>> {
        return repository.getSourcesWithNonLibraryAnime()
    }
}
